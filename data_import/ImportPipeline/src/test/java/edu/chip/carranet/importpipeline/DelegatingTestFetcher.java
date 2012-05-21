package edu.chip.carranet.importpipeline;

import edu.chip.carranet.importpipeline.dispatch.Dispatchable;
import edu.chip.carranet.importpipeline.fetch.Fetcher;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * A test fetcher that wraps another and fails when exceptions leak through
 *
 * @author Justin Quan
 * @link http://chip.org
 * Date: 3/7/11
 */
public class DelegatingTestFetcher extends LatchedThrowableCollector implements Fetcher {
    private Fetcher delegate;

    public DelegatingTestFetcher(Fetcher delegate, CountDownLatch latch) {
        super(latch);
        this.delegate = delegate;
    }

    @Override
    public Dispatchable fetch() throws FetchException {
        try {
            return delegate.fetch();
        } catch (FetchException e) {
            addThrowable(e);
            throw e;
        } finally {
            latch.countDown();
        }
    }

    @Override
    public long getFetchPeriod() {
        return delegate.getFetchPeriod();
    }

    @Override
    public TimeUnit getFetchTimeUnit() {
        return delegate.getFetchTimeUnit();
    }
}
