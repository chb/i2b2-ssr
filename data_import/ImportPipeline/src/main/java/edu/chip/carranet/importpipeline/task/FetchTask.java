package edu.chip.carranet.importpipeline.task;

import edu.chip.carranet.importpipeline.dispatch.Dispatchable;
import edu.chip.carranet.importpipeline.fetch.Fetcher;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;

/**
 * The task that executes the fetching of edu.chip.carranet.data.  The details of the fetch
 * are delegated to a fetcher, and our only order of business here is to
 * take the fetched result and plop it down into a process queue.
 *
 *
 * @author Justin Quan
 * Date: Oct 19, 2010
 */
public class FetchTask implements Runnable {
    private static Logger log = Logger.getLogger(FetchTask.class);

    private BlockingQueue<Dispatchable> dataQueue;
    private Fetcher fetcher;

    public FetchTask(BlockingQueue<Dispatchable> dataQueue, Fetcher fetcher) {
        this.dataQueue = dataQueue;
        this.fetcher = fetcher;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        try {
            Dispatchable data = fetcher.fetch();

            // block if queue is full.
            if(data == null) {
                log.info("fetched null data so i'll ignore it, fetcher:" + fetcher.getClass());
            } else {
                dataQueue.put(data);
            }
        } catch (Throwable t) {
            log.fatal("Problem fetching data", t);
            throw new RuntimeException(t);
        } finally {
            log.info("Fetch Task completed in " + (System.currentTimeMillis()-start) + "ms");
        }
    }

}
