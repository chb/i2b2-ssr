package edu.chip.carranet.importpipeline.dispatch;

import edu.chip.carranet.importpipeline.IngestionEngine;
import edu.chip.carranet.importpipeline.process.Processor;
import edu.chip.carranet.importpipeline.task.ProcessTask;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * This dispatcher takes a edu.chip.carranet.data item and enqueues a processing task for it.
 *
 * It needs to figure out what to do with the edu.chip.carranet.data, then create the appropriate
 * task to process it.  This task will probably also need to know where the output
 * of the processed edu.chip.carranet.data is.
 *
 * @author Justin Quan
 * Date: Oct 19, 2010
 */
public class FetchedDataDispatcher extends Dispatcher {
    private static final Logger log = Logger.getLogger(FetchedDataDispatcher.class);

    private Map<Dispatchable.DispatchType,Processor> processorMap;
    private BlockingQueue<Dispatchable> outputData;

    public FetchedDataDispatcher(IngestionEngine engine, String name, BlockingQueue<Dispatchable> inputQueue,
                                 BlockingQueue<Runnable> outputTaskQueue,
                                 BlockingQueue<Dispatchable> outputData, Map<Dispatchable.DispatchType,Processor> processorMap) {
        super(engine, name, inputQueue, outputTaskQueue);
        this.processorMap = processorMap;
        this.outputData = outputData;
    }

    @Override
     public Runnable dispatch(Dispatchable work) throws DispatchException {
        log.debug("fetch dispatching");
        if(processorMap.isEmpty())
            throw new RuntimeException();  // TODO

        Processor processor = processorMap.get(work.getDispatchType());
        if(processor == null) {
            throw new DispatchException("unable to find a suitable dispatcher for dispatchType:"+work.getDispatchType());
        }

        return new ProcessTask(outputData, processor, work);
    }
}
