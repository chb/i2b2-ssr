package edu.chip.carranet.importpipeline.dispatch;

import edu.chip.carranet.importpipeline.IngestionEngine;
import edu.chip.carranet.importpipeline.output.Outputter;
import edu.chip.carranet.importpipeline.task.OutputTask;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * This class takes a edu.chip.carranet.data item and enqueues an output task for it.
 *
 * @author Justin Quan
 * Date: Oct 19, 2010
 */
public class ProcessedDataDispatcher extends Dispatcher {
    private static final Logger log = Logger.getLogger(ProcessedDataDispatcher.class);

    private Map<Dispatchable.DispatchType,Outputter> outputterMap;

    public ProcessedDataDispatcher(IngestionEngine engine, String name, BlockingQueue<Dispatchable> inputQueue,
                                   BlockingQueue<Runnable> outputTaskQueue,
                                   Map<Dispatchable.DispatchType,Outputter> outputterMap) {
        super(engine, name, inputQueue, outputTaskQueue);
        this.outputterMap = outputterMap;
    }

    @Override
    public Runnable dispatch(Dispatchable work) throws DispatchException {
        log.debug("process dispatching");
        if(outputterMap.isEmpty())
            throw new RuntimeException();  // TODO

        Outputter outputter = outputterMap.get(work.getDispatchType());
        if(outputter == null) {
            throw new DispatchException("unable to find a suitable dispatcher for dispatchType:"+work.getDispatchType());
        }


        return new OutputTask<Dispatchable>(work, outputter);
    }
}
