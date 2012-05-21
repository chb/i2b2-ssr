package edu.chip.carranet.carradatapipeline.transactionstore;

/**
 * This interface defines a edu.chip.carranet.data store who's job is to track how many
 * Inform ODM Adapter getTransaction() calls we've processed, so we
 * can safely resume processing at the appropriate place if we are
 * ever interrupted.
 *
 * @author Justin Quan
 * Date: 3/1/11
 */
public interface InformTransactionStore {
    public void save(InformTransactionRecord transactionRecord) throws TransactionException;
    public String loadLastBookmark(String trial) throws TransactionException;
}
