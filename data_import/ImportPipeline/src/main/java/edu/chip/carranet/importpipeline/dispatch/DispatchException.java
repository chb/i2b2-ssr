package edu.chip.carranet.importpipeline.dispatch;

/**
 * @justinquan
 * Date: 3/2/11
 */
public class DispatchException extends Exception {
    public DispatchException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public DispatchException(String s) {
        super(s);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public DispatchException(String s, Throwable throwable) {
        super(s, throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public DispatchException(Throwable throwable) {
        super(throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
