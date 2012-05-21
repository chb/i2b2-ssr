package edu.chip.carranet.importpipeline.task;

import edu.chip.carranet.importpipeline.dispatch.Dispatchable;
import edu.chip.carranet.importpipeline.output.Outputter;
import org.apache.log4j.Logger;

/**
 * @author Justin Quan
 * Date: Oct 19, 2010
 */
public class OutputTask<T extends Dispatchable> implements Runnable {
    private static final Logger log = Logger.getLogger(OutputTask.class);

    private T data;
    private Outputter outputter;

    public OutputTask(T data, Outputter outputter) {
        this.data = data;
        this.outputter = outputter;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        try {
            outputter.output(data);
        } catch (Throwable t) {
            log.fatal("failed to output data", t);
            throw new RuntimeException(t);
        } finally {
            log.info("Output Task completed in " + (System.currentTimeMillis()-start) + "ms");
        }
    }
}
