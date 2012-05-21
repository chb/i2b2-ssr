package edu.chip.carranet.carradatapipeline.transactionstore;

import oracle.jdbc.driver.OracleConnection;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * @author Justin Quan
 * @link http://chip.org
 * Date: 3/10/11
 */
public class SiteTransactionDBStore implements SiteTransactionStore {
    private static final Logger log = Logger.getLogger(SiteTransactionDBStore.class);

    private static final String TRIAL_COLUMN_NAME = "TRIAL";
    private static final String BOOKMARK_COLUMN_NAME = "BOOKMARK";
    private static final String SITE_COLUMN_NAME = "SITE";
    private static final String PROCESS_DATE_COLUMN_NAME = "PROCESSDATE";

    private String connectionString;
    private String userName;
    private String password;
    private String tableName;

    public SiteTransactionDBStore(String connectionString, String userName, String password, String tableName) {
        this.connectionString = connectionString;
        this.userName = userName;
        this.password = password;
        this.tableName = tableName;
    }

    public static void createTable(Connection connection, String tableName) throws SQLException {
        final String createImportTableQuery = "CREATE TABLE " + tableName + " (\n" +
                TRIAL_COLUMN_NAME + " VARCHAR2(100), \n" +
                BOOKMARK_COLUMN_NAME + " VARCHAR2(100), \n" +
                SITE_COLUMN_NAME + " VARCHAR2(100), \n" +
                PROCESS_DATE_COLUMN_NAME + " timestamp )";
        connection.createStatement().execute(createImportTableQuery);
        connection.commit();
    }

    @Override
    public void saveSiteTransaction(String site, InformTransactionRecord transactionRecord) throws TransactionException {
        if(transactionRecord.getTrial() == null ||
                transactionRecord.getBookmarkValue() == null) {
            throw new IllegalArgumentException("can't have null trial/bookmark value in transactionRecord:" + transactionRecord);
        }
        if(site == null) {
            throw new IllegalArgumentException("can't have null site value");
        }

        try {
            Connection connection =
                    DriverManager.getConnection(connectionString,
                            userName,
                            password);
            connection.setAutoCommit(false);

            String timestamp = new Timestamp(System.currentTimeMillis()).toString();
            if(connection instanceof OracleConnection) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSS a");
                timestamp = formatter.format(new Date(System.currentTimeMillis()));
            }
            String sql = "INSERT INTO " + tableName + " VALUES (" +
                    "'" + transactionRecord.getTrial() + "', " +
                    "'" + transactionRecord.getBookmarkValue() + "', " +
                    "'" + site + "', " +
                    "'" + timestamp + "')";
            connection.createStatement().execute(sql);
            connection.commit();
        } catch (SQLException e) {
            throw new TransactionException(e);
        }
    }

    @Override
    public boolean isSiteTransactionComplete(String site, InformTransactionRecord transactionRecord) throws TransactionException {
        if(transactionRecord.getTrial() == null ||
                transactionRecord.getBookmarkValue() == null) {
            throw new IllegalArgumentException("can't have null trial/bookmark value in transactionRecord:" + transactionRecord);
        }
        if(site == null) {
            throw new IllegalArgumentException("can't have null site value");
        }

        try {
            Connection connection =
                    DriverManager.getConnection(connectionString,
                            userName,
                            password);
            connection.setAutoCommit(false);

            String sql = "SELECT * FROM " + tableName + " WHERE " +
                    TRIAL_COLUMN_NAME + "='" + transactionRecord.getTrial() + "' AND " +
                    BOOKMARK_COLUMN_NAME + "='" + transactionRecord.getBookmarkValue() + "' AND " +
                    SITE_COLUMN_NAME + "='" + site + "'";
            ResultSet result = connection.createStatement().executeQuery(sql);
            // do we need to commit here???
            return result.next();
        } catch (SQLException e) {
            throw new TransactionException(e);
        }
    }
}
