package edu.chip.carranet.importpipeline.process;

/**
 * @author Justin Quan
 * @link http://chip.org Date: 3/7/11
 */
public class ProcessException extends Exception {
    public ProcessException() {
    }

    public ProcessException(String message) {
        super(message);
    }

    public ProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessException(Throwable cause) {
        super(cause);
    }
}
