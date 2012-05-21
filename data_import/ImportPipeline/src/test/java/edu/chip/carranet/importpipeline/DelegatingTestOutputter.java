package edu.chip.carranet.importpipeline;

import edu.chip.carranet.importpipeline.dispatch.Dispatchable;
import edu.chip.carranet.importpipeline.output.Outputter;
import edu.chip.carranet.importpipeline.output.OutputterException;

import java.util.concurrent.CountDownLatch;

/**
 * A test outputter that delegates to another, while failing on uncaught exceptions
 * @author Justin Quan
 * @link http://chip.org
 * Date: 3/7/11
 */
public class DelegatingTestOutputter<T extends Dispatchable> extends LatchedThrowableCollector implements Outputter<T>{
    private Outputter<T> delegate;

    public DelegatingTestOutputter(Outputter<T> delegate, CountDownLatch latch) {
        super(latch);
        this.delegate = delegate;
    }

    @Override
    public void output(T data) throws OutputterException {
        try {
            delegate.output(data);
        } catch(OutputterException e) {
            addThrowable(e);
            throw e;
        } finally {
            latch.countDown();
        }
    }
}
