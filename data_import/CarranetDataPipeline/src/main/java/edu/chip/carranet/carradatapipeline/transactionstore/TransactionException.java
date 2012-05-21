package edu.chip.carranet.carradatapipeline.transactionstore;

/**
 * @author Justin Quan
 * Date: 3/1/11
 */
public class TransactionException extends Exception {
    public TransactionException() {
        super();
    }

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }
}
