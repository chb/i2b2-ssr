package edu.chip.carranet.importpipeline.output;

/**
 * @author Justin Quan
 * @link http://chip.org Date: 3/7/11
 */
public class OutputterException extends Exception {
    public OutputterException() {
    }

    public OutputterException(String message) {
        super(message);
    }

    public OutputterException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutputterException(Throwable cause) {
        super(cause);
    }
}
