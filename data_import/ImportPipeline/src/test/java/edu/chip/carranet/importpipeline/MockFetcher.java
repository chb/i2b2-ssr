package edu.chip.carranet.importpipeline;

import edu.chip.carranet.importpipeline.dispatch.Dispatchable;
import edu.chip.carranet.importpipeline.fetch.Fetcher;
import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: justinquan
 * Date: 2/16/11
 * Time: 1:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class MockFetcher implements Fetcher {
    private static final Logger log = Logger.getLogger(MockFetcher.class);
    private Random rand = new Random();

    public static class StringData extends Dispatchable {
        String s;

        public StringData(String s) {
            this.s = s;
        }

        @Override
        public DispatchType getDispatchType() {
            return Dispatchable.DispatchType.TEST;
        }
    }

    @Override
    public StringData fetch() throws FetchException {
        return new StringData(Long.toString(rand.nextLong()));
    }

    @Override
    public long getFetchPeriod() {
        return 10;
    }

    @Override
    public TimeUnit getFetchTimeUnit() {
        return TimeUnit.MILLISECONDS;
    }
}
