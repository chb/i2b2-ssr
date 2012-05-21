package edu.chip.carranet.importpipeline.config;

import java.util.concurrent.TimeUnit;

/**
 * @author Justin Quan
 * @version %I% Date: 2/25/11
 */
public class FetchConfig {
    public static FetchConfig DEFAULT_FETCH_CONFIG = new FetchConfig(1, TimeUnit.SECONDS);

    private long fetchPeriod;
    private TimeUnit fetchTimeUnit;

    public FetchConfig(long fetchPeriod, TimeUnit fetchTimeUnit) {
        this.fetchPeriod = fetchPeriod;
        this.fetchTimeUnit = fetchTimeUnit;
    }

    public long getFetchPeriod() {
        return fetchPeriod;
    }

    public TimeUnit getFetchTimeUnit() {
        return fetchTimeUnit;
    }
}
