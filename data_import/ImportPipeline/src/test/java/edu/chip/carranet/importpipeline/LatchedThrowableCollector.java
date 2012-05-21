package edu.chip.carranet.importpipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author Justin Quan
 * @link http://chip.org Date: 3/7/11
 */
public abstract class LatchedThrowableCollector {
    protected List<Throwable> throwableList;
    protected CountDownLatch latch;

    protected LatchedThrowableCollector(CountDownLatch latch) {
        this.throwableList = new ArrayList<Throwable>();
        this.latch = latch;
    }

    protected synchronized void addThrowable(Throwable t) {
        throwableList.add(t);
    }

    public synchronized void assertOK() throws Exception {
        if(!throwableList.isEmpty()) {
            throw new Exception("Found " + throwableList.size() +
                    " problems, attaching the first", throwableList.get(0));
        }
    }

    public List<Throwable> getThrowableList() {
        return throwableList;
    }
}
