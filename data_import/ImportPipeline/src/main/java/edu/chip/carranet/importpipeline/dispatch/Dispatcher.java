package edu.chip.carranet.importpipeline.dispatch;

import edu.chip.carranet.importpipeline.IngestionEngine;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;

/**
 * A Dispatcher continually takes work off from a queue and dispatches it.
 * The details of the dispatch is to be determined by the extender of this class.
 * In general, since all this dispatch work is being done by a single thread, it's
 * best to keep the amount of work done in dispatch to a minimum.
 *
 * @author Justin Quan
 * Date: Oct 19, 2010
 */
public abstract class Dispatcher extends Thread {
    private static final Logger log = Logger.getLogger(Dispatcher.class);
    protected BlockingQueue<Dispatchable> inputQueue;  // consume edu.chip.carranet.data
    protected BlockingQueue<Runnable> outputTaskQueue; // produce tasks
    protected IngestionEngine engine;


    public Dispatcher(IngestionEngine engine, String name, BlockingQueue<Dispatchable> inputQueue, BlockingQueue<Runnable> outputTaskQueue) {
        this.engine = engine;
        this.inputQueue = inputQueue;
        this.outputTaskQueue = outputTaskQueue;
        super.setDaemon(true);
        super.setName(name);
    }

    @Override
    public void run() {
        try {
            for(;;) {
                try {
                    Dispatchable work = inputQueue.take();
                    Runnable task = dispatch(work);
                    outputTaskQueue.put(task);
                } catch (InterruptedException e) {
                    log.info("dispatch thread "+getName()+" was interrupted", e);
                }
            }
        } catch (Throwable t) {
            log.fatal("mission critical dispatcher has died, shutting down the IngestionEngine now!", t);
            engine.stop();
        }
    }

    public abstract Runnable dispatch(Dispatchable work) throws DispatchException;
}
