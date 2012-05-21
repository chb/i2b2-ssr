package edu.chip.carranet.importpipeline.task;

import edu.chip.carranet.importpipeline.dispatch.Dispatchable;
import edu.chip.carranet.importpipeline.process.Processor;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;

/**
 * Take the incoming edu.chip.carranet.data, process it according to the processor,
 * and place the result onto a queue.
 *
 * @author Justin Quan
 *          Date: Oct 19, 2010
 */
public class ProcessTask implements Runnable {
    private static final Logger log = Logger.getLogger(ProcessTask.class);

    private BlockingQueue<Dispatchable> outputQueue;
    private Processor processor;
    private Dispatchable incomingData;

    public ProcessTask(BlockingQueue<Dispatchable> outputQueue, Processor processor, Dispatchable incomingData) {
        this.outputQueue = outputQueue;
        this.processor = processor;
        this.incomingData = incomingData;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        try {
            Dispatchable processedData = processor.process(incomingData);
            if(processedData == null) {
                log.info("processed null data so i'll ignore it, processor:" + processor.getClass());
            } else {
                outputQueue.put(processedData);
            }
        } catch (Throwable t) {
            log.fatal("Problem processing data", t);
            throw new RuntimeException(t);
        } finally {
            log.info("Process Task completed in " + (System.currentTimeMillis()-start) + "ms");
        }
    }
}
