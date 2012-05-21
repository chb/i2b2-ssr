package edu.chip.carranet.importpipeline.fetch;

import edu.chip.carranet.importpipeline.dispatch.Dispatchable;

import java.util.concurrent.TimeUnit;

/**
 * @author Justin Quan
 *          Date: Oct 19, 2010
 */
public interface Fetcher {
    class FetchException extends Exception {
        public FetchException() {
            super();
        }

        public FetchException(String s) {
            super(s);
        }

        public FetchException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public FetchException(Throwable throwable) {
            super(throwable);
        }
    }

    public Dispatchable fetch() throws FetchException;

    /**
     * 
     * @return
     */
    public long getFetchPeriod();
    public TimeUnit getFetchTimeUnit();
}
