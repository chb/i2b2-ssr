package edu.chip.carranet.importpipeline;

import edu.chip.carranet.importpipeline.dispatch.Dispatchable;
import edu.chip.carranet.importpipeline.process.ProcessException;
import edu.chip.carranet.importpipeline.process.Processor;

import java.util.concurrent.CountDownLatch;

/**
 * A processor that delegates to another, but will catch and call fail on junits
 *
 * @author Justin Quan
 * @link http://chip.org
 * Date: 3/7/11
 */
public class DelegatingTestProcessor<I extends Dispatchable,O extends Dispatchable> extends LatchedThrowableCollector implements Processor<I,O>{
    Processor<I,O> delegate;

    public DelegatingTestProcessor(Processor<I, O> delegate, CountDownLatch latch) {
        super(latch);
        this.delegate = delegate;
    }
    @Override
    public O process(I data) throws ProcessException {
        try {
            return delegate.process(data);
        } catch (ProcessException e) {
            addThrowable(e);
            throw e;
        } finally {
            latch.countDown();
        }
    }
}
