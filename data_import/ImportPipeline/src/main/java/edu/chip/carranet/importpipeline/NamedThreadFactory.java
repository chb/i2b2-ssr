package edu.chip.carranet.importpipeline;

import java.util.concurrent.ThreadFactory;

/**
 * Simple thread factory that'll use the name you pass in
 * for the threads it creates.
 *
 * @author Justin Quan
 *          Date: Oct 19, 2010
 */
public class NamedThreadFactory implements ThreadFactory {

    private String name;

    public NamedThreadFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread t = new Thread(runnable, name);
        t.setDaemon(true);
        return t;
    }
}
