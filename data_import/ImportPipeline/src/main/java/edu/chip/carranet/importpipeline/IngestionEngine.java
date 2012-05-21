package edu.chip.carranet.importpipeline;


import edu.chip.carranet.importpipeline.dispatch.Dispatchable;
import edu.chip.carranet.importpipeline.dispatch.Dispatcher;
import edu.chip.carranet.importpipeline.dispatch.FetchedDataDispatcher;
import edu.chip.carranet.importpipeline.dispatch.ProcessedDataDispatcher;
import edu.chip.carranet.importpipeline.fetch.Fetcher;
import edu.chip.carranet.importpipeline.output.Outputter;
import edu.chip.carranet.importpipeline.process.Processor;
import edu.chip.carranet.importpipeline.task.FetchTask;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This engine drives all of the processing for the import pipeline.  It is made up of
 * 2 producer consumer queues.  The first producers are the Fetchers which are responsible
 * for obtaining edu.chip.carranet.data.  Once fetched, the edu.chip.carranet.data is dropped of into the fetchedDataQueue.  The first consumer
 * are the Processors which take this edu.chip.carranet.data and do some work on it.  The Processors are also the
 * second producers, as once they are finished working on the fetched edu.chip.carranet.data, they drop it off in
 * an output queue.  The second and final consumers are the Outputters which take edu.chip.carranet.data from the
 * output queue and send it somewhere.
 *
 * In order to provide logical decoupling between the producers and consumers, dispatchers were
 * created to process the edu.chip.carranet.data queues.  They are responsible for figuring out what to do with
 * the edu.chip.carranet.data and how to move it to the next set of workers.
 *
 *
 * //TODO: is this the best experience for a user of this engine?  Seems too complicated
 * In order to add your own implementation, you'll need to create your own implementations of
 * a fetcher, processor and outputter.  Then you'll need to wire up the dispatchers to recognize
 * your edu.chip.carranet.data and send them off to the appropriate workers.  Finally, you'll need to ensure that
 * this engine loads up your new edu.chip.carranet.data handlers.
 *
 *
 *
 * @author Justin Quan
 *          Date: Oct 19, 2010
 */
public class IngestionEngine {
    private static final Logger log = Logger.getLogger(IngestionEngine.class);

    // fetchers
    private List<Fetcher> fetchers;

    // collecting threads
    private ScheduledThreadPoolExecutor fetchingExecutor;

    // fetched edu.chip.carranet.data queue
    private BlockingQueue<Dispatchable> fetchedDataQueue;

    // fetched edu.chip.carranet.data dispatcher
    private Dispatcher fetchedDataDispatcher;

    // input queue
    private BlockingQueue<Runnable> processorQueue;

    // worker threads
    private ThreadPoolExecutor processingExecutor;

    // processed edu.chip.carranet.data queue
    private BlockingQueue<Dispatchable> processedDataQueue;

    // processed edu.chip.carranet.data dispatcher
    private Dispatcher processedDataDispatcher;

    // output queue
    private BlockingQueue<Runnable> outgoingQueue;

    // output workers
    private ThreadPoolExecutor deliveryExecutor;

    private AtomicBoolean isShuttingDown;

    private CountDownLatch shutDownLatch;



    public IngestionEngine(List<Fetcher> fetchers, Map<Dispatchable.DispatchType,Processor> processors,
                           Map<Dispatchable.DispatchType,Outputter> outputters) {
        // TODO: springify concrete instantiations

        this.fetchers = new ArrayList<Fetcher>(fetchers);
        this.fetchingExecutor = new ScheduledThreadPoolExecutor(fetchers.size(), new NamedThreadFactory("fetcher")) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                // i can't believe this is the recommended work around
                // See http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6459119
                if(r instanceof Future<?>) {
                    try {
                        // this future is the future for all recurring executions, which
                        // means it gets reset after execution and keeps no result.  run
                        // get() merely to allow the saved exception to be thrown.
                        ((Future<?>)r).get(1, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // ignore/reset
                    } catch (ExecutionException e) {
                        t = e;
                    } catch (TimeoutException e) {
                        // ignore
                    }
                }
                shutDownOnError(r,t);
            }
        };
        this.fetchedDataQueue = new LinkedBlockingQueue<Dispatchable>();

        this.processorQueue = new LinkedBlockingQueue<Runnable>();
        this.processingExecutor = new ThreadPoolExecutor(2, 10, 30, TimeUnit.SECONDS, processorQueue, new NamedThreadFactory("processor")) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                shutDownOnError(r,t);
            }
        };
        this.processedDataQueue = new LinkedBlockingQueue<Dispatchable>();

        this.outgoingQueue = new LinkedBlockingQueue<Runnable>();

        this.fetchedDataDispatcher = new FetchedDataDispatcher(this, "fetchedDataDispatcher", fetchedDataQueue,
                processorQueue, processedDataQueue, processors);

        this.processedDataDispatcher = new ProcessedDataDispatcher(this, "processedDataDispatcher",
                processedDataQueue, outgoingQueue, outputters);

        this.deliveryExecutor = new ThreadPoolExecutor(2, 10, 30, TimeUnit.SECONDS, outgoingQueue, new NamedThreadFactory("ouputter")) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                shutDownOnError(r,t);
            }
        };

        this.isShuttingDown = new AtomicBoolean(false);
        this.shutDownLatch = new CountDownLatch(1);

    }

    // TODO: add configurable constructor

    private void shutDownOnError(Runnable r, Throwable t) {
        if(t != null) {
            log.fatal("Execution produced a fatal error, shutting down the IngestionEngine now! Runnable:"+r.getClass(), t);
            new Thread("shutterdowner") {
                @Override
                public void run() {
                    IngestionEngine.this.stop();
                }
            }.start();
        }
    }

    public void start() {
        for(Fetcher fetcher : fetchers) {
            FetchTask fetchTask = new FetchTask(fetchedDataQueue, fetcher);
            fetchingExecutor.scheduleAtFixedRate(fetchTask, 0, fetcher.getFetchPeriod(), fetcher.getFetchTimeUnit());
        }
        this.fetchedDataDispatcher.start();
        this.processingExecutor.prestartCoreThread();
        this.processedDataDispatcher.start();
        this.deliveryExecutor.prestartCoreThread();
    }

    public void stop() {
        boolean areWeStopped = isShuttingDown.getAndSet(true);
        if(areWeStopped) {
            return;
        }

        // TODO: consider other less slow options to shutdown
        shutdownAndAwaitTermination(fetchingExecutor);
        shutdownAndAwaitTermination(processingExecutor);
        shutdownAndAwaitTermination(deliveryExecutor);
        shutDownLatch.countDown();
    }

    private void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(60, TimeUnit.SECONDS))
                    log.error("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            List<Runnable> canceled = pool.shutdownNow();
            log.info("Number of t" +
                    "asks prematurely canceled:" + canceled.size());
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    public boolean isShuttingDown() {
        return isShuttingDown.get();
    }

    public CountDownLatch getShutDownLatch() {
        return shutDownLatch;
    }
}
