package edu.chip.carranet.carradatapipeline.transactionstore;

import oracle.jdbc.driver.OracleConnection;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * A database backed implementation of the InformTransactionStore
 *
 * Hopefully the handwritten SQL and edu.chip.carranet.data types are generic enough to
 * be put to any DB, but this code has really only been tested against
 * an in memory H2, and Oracle.
 *
 * @author Justin Quan
 */
public class InformTransactionDBStore implements InformTransactionStore {
    private static final Logger log = Logger.getLogger(InformTransactionDBStore.class);

    private static final String TRIAL_COLUMN_NAME = "TRIAL";
    private static final String BOOKMARK_COLUMN_NAME = "BOOKMARK";
    private static final String FETCH_DATE_COLUMN_NAME = "FETCHDATE";
    private static final String PROCESS_DATE_COLUMN_NAME = "PROCESSDATE";
    private static final String NEXT_BOOKMARK_COLUMN_NAME = "NEXTBOOKMARK";
    private static final String REPORT_COLUMN_NAME = "REPORT";
    private Connection connection;

    private String tableName;

    public InformTransactionDBStore(Connection connection, String tableName) throws SQLException {
        this.connection = connection;
        this.tableName = tableName;

        if(connection.getAutoCommit()) {
            throw new IllegalArgumentException("InformTransactionDBStore does not work with autocommiting sql connections");
        }
    }

    public InformTransactionDBStore(String connectionString, String userName, String password, String tableName) throws TransactionException {
        try {
            connection =
                    DriverManager.getConnection(connectionString,
                            userName,
                            password);
            connection.setAutoCommit(false);

        } catch (SQLException e) {
            throw new TransactionException(e);
        }
        this.tableName = tableName;
    }


    public static void createTable(Connection connection, String tableName) throws SQLException {
        final String createImportTableQuery = "CREATE TABLE " + tableName + " (\n" +
                TRIAL_COLUMN_NAME + " VARCHAR2(100), \n" +
                BOOKMARK_COLUMN_NAME + " VARCHAR2(100), \n" +
                FETCH_DATE_COLUMN_NAME + " timestamp, \n" +
                PROCESS_DATE_COLUMN_NAME + " timestamp, \n" +
                NEXT_BOOKMARK_COLUMN_NAME + " VARCHAR2(100), \n" +
                REPORT_COLUMN_NAME + " CLOB )";
        connection.createStatement().execute(createImportTableQuery);
        connection.commit();
    }

    public void save(InformTransactionRecord transactionRecord) throws TransactionException {
        if( transactionRecord.getTrial() == null ||
                transactionRecord.getBookmarkValue() == null ||
                transactionRecord.getFetchDate() == null ||
                transactionRecord.getProcessedDate() == null ) {
            throw new IllegalArgumentException("can't have null values in transactionRecord:" + transactionRecord);
        }

        try {
            String fetchTime = transactionRecord.getFetchDate().toString();
            String processTime = transactionRecord.getProcessedDate().toString();

            if(connection instanceof OracleConnection) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSS a");
                fetchTime = formatter.format(new Date(transactionRecord.getFetchDate().getTime()));
                processTime = formatter.format(new Date(transactionRecord.getProcessedDate().getTime()));
            }
            PreparedStatement ps = connection.prepareStatement("INSERT INTO "+ tableName +" VALUES(?, ?, ?, ?, ?, ?)");
            ps.setString(1, transactionRecord.getTrial());
            ps.setString(2, transactionRecord.getBookmarkValue());
            ps.setString(3, fetchTime);
            ps.setString(4, processTime);
            ps.setString(5, transactionRecord.getNextBookmark());
            ps.setString(6, transactionRecord.getAuditReport());
            ps.execute();
            ps.close();
            connection.commit();
        } catch (SQLException e) {
            throw new TransactionException(e);
        }
    }

    public String loadLastBookmark(String trial) throws TransactionException {
        try {
            String sql = "SELECT * FROM " + tableName +
                    " WHERE " + TRIAL_COLUMN_NAME + "='" + trial +
                    "' ORDER BY " + PROCESS_DATE_COLUMN_NAME + " DESC";
            ResultSet result = connection.createStatement().executeQuery(sql);
            return result.next() ? result.getString(NEXT_BOOKMARK_COLUMN_NAME) : InformTransactionRecord.BOOKMARK_START;
        } catch (SQLException e) {
            throw new TransactionException(e);
        }
    }
}
