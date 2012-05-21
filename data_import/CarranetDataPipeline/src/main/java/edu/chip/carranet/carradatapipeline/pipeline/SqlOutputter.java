package edu.chip.carranet.carradatapipeline.pipeline;

import edu.chip.carranet.carradatapipeline.sitemap.ISiteMap;
import edu.chip.carranet.carradatapipeline.transactionstore.SiteTransactionStore;
import edu.chip.carranet.carradatapipeline.transactionstore.TransactionException;
import edu.chip.carranet.importpipeline.output.Outputter;
import edu.chip.carranet.importpipeline.output.OutputterException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Placeholder for integration purposes.
 */
public class SqlOutputter implements Outputter<SqlCommands> {
    private static final Logger log = Logger.getLogger(SqlOutputter.class);
    private FetchFromInform informFetcher;
    private SiteTransactionStore siteStore;
    private ISiteMap siteMap;
    private boolean doBatch;


    public SqlOutputter(FetchFromInform informFetcher, SiteTransactionStore siteStore, ISiteMap siteMap) throws SQLException {
        this.informFetcher = informFetcher;
        this.siteStore = siteStore;
        this.siteMap = siteMap;
        this.doBatch = true;
    }

    @Override
    public void output(final SqlCommands data) throws OutputterException {
        log.info("outputting inform data");
        try {
            List<ExceptionSavingThread> siteWorkers = new ArrayList<ExceptionSavingThread>(data.getSqlCommands().size());
            for (final String site : data.getSqlCommands().keySet()) {
                if(siteStore.isSiteTransactionComplete(site, data.getTransactionRecord())) {
                    log.info("skipping already processed site:" + site);
                    continue;
                }

                ExceptionSavingThread t = new ExceptionSavingThread("siteworker-"+site) {
                    @Override
                    public void run() {
                        try {
                            Connection connection = null;
                            try {
                                connection = getConnection(site);
                                if(connection != null) {
                                    String statements = data.getSqlCommands().get(site);
                                    if(doBatch) {
                                        executeSQLChunkInBatches(connection, statements);
                                    } else {
                                        executeSQLChunk(connection, statements);
                                    }
                                }
                            } finally {
                                if(connection !=null) {
                                    connection.close();
                                }
                            }
                            // update transaction record and save it
                            siteStore.saveSiteTransaction(site, data.getTransactionRecord());
                        } catch (Exception e) {
                            savedException = e;
                        }
                    }
                };

                siteWorkers.add(t);
            }

            for(Thread t : siteWorkers) {
                t.setDaemon(true);
                t.start();
            }
            for(Thread t : siteWorkers) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    log.info("thread " + t.getName() + " was interrupted!", e);
                }
            }
            for(ExceptionSavingThread t : siteWorkers) {
                if(t.savedException != null) {
                    throw new OutputterException("problem processing site data, thread: " + t.getName(),t.savedException);
                }
            }

        } catch (TransactionException e) {
            throw new OutputterException(e);
        }

        // done!  let's save this transaction
        try {
            data.getTransactionRecord().setProcessedDate(new Timestamp(System.currentTimeMillis()));
            informFetcher.notifyProcessed(data.getTransactionRecord());
        } catch (TransactionException e) {
            // TODO: re think failure scenario, maybe retries?
            throw new OutputterException(e);
        }
    }

    private Connection getConnection(String id) throws SQLException {
        Connection connection = siteMap.getConnection(id);
        if (connection == null) {
            log.warn("Undefined connection for site id " + id);
        } else {
            connection.setAutoCommit(false);
        }
        return connection;
    }

    public void setDoBatch(boolean doBatch) {
        this.doBatch = doBatch;
    }

    private static void executeSQLChunk(Connection connection, String sqlChunk) throws OutputterException {
        String lastStatement = "";
        try {
            Statement sqlStatement = connection.createStatement();
            for(String statement : sqlChunk.split(";")) {
                if(statement.replace(" ", "").replace("\n", "").replace("\t","").isEmpty()) {
                    continue;
                }
                lastStatement = statement.replace(";", "");
                sqlStatement.execute(lastStatement);
                connection.commit();
            }
        } catch (SQLException e) {
            throw new OutputterException(lastStatement, e);
        }
    }

    private static void executeSQLChunkInBatches(Connection connection, String sqlChunk) throws OutputterException {
        try {
            Statement sqlStatement = connection.createStatement();
            for(String statement : sqlChunk.split(";")) {
                if(statement.replace(" ", "").replace("\n", "").replace("\t","").isEmpty()) {
                    continue;
                }
                sqlStatement.addBatch(statement.replace(";", ""));
            }
            sqlStatement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            throw new OutputterException(e);
        }
    }

    private class ExceptionSavingThread extends Thread {
        Exception savedException;

        ExceptionSavingThread(String name) {
            super(name);
        }
    }
}
