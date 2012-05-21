package edu.chip.carranet.carradatapipeline.transactionstore;

/**
 * @author Justin Quan
 * @link http://chip.org
 * Date: 3/10/11
 */
public interface SiteTransactionStore {

    public void saveSiteTransaction(String site, InformTransactionRecord transactionRecord) throws TransactionException;
    public boolean isSiteTransactionComplete(String site, InformTransactionRecord transactionRecord) throws TransactionException;
}
