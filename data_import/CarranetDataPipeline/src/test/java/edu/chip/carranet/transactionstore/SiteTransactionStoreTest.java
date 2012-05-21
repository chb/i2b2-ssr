package edu.chip.carranet.transactionstore;

import edu.chip.carranet.carradatapipeline.transactionstore.InformTransactionRecord;
import edu.chip.carranet.carradatapipeline.transactionstore.SiteTransactionDBStore;
import edu.chip.carranet.carradatapipeline.transactionstore.SiteTransactionStore;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * @author Justin Quan
 * @link http://chip.org Date: 3/10/11
 */

public class SiteTransactionStoreTest {
    private final static String TABLE_NAME = "siteTable";
    private final static String USERNAME = "";
    private final static String PASSWORD = "";

    private SiteTransactionStore store;
    private Connection connection;

    @BeforeClass
    public static void staticSetup() throws Exception {
        Class.forName("org.h2.Driver");
    }

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testDB;DB_CLOSE_DELAY=-1";
        connection = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
        connection.setAutoCommit(false);
        SiteTransactionDBStore.createTable(connection, TABLE_NAME);
        store = new SiteTransactionDBStore(connectionString, USERNAME, PASSWORD, TABLE_NAME);

    }

    @After
    public void tearDown() throws Exception {
        connection.createStatement().execute("DROP TABLE " + TABLE_NAME);
        connection.commit();
    }


    @Test
    public void testHappyCase() throws Exception {
        String trial = "trial";
        String bookmark = "bookmark";
        Timestamp fetchDate = new Timestamp(System.currentTimeMillis());
        Timestamp processedDate = new Timestamp(System.currentTimeMillis());
        String nextBookmark = "nextBookmark";
        String auditData = "audit";
        InformTransactionRecord record = new InformTransactionRecord(trial, bookmark, fetchDate, processedDate, nextBookmark, auditData);

        String site1 = "site1";

        store.saveSiteTransaction(site1, record);
        assertTrue(store.isSiteTransactionComplete(site1, record));

        String site2 = "site2";
        assertFalse(store.isSiteTransactionComplete(site2, record));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullSite() throws Exception {
        String trial = "trial";
        String bookmark = "bookmark";
        Timestamp fetchDate = new Timestamp(System.currentTimeMillis());
        Timestamp processedDate = new Timestamp(System.currentTimeMillis());
        String nextBookmark = "nextBookmark";
        String auditData = "audit";
        InformTransactionRecord record = new InformTransactionRecord(trial, bookmark, fetchDate, processedDate, nextBookmark, auditData);
        store.saveSiteTransaction(null, record);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullRecordValue() throws Exception {
        String trial = null;
        String bookmark = "bookmark";
        Timestamp fetchDate = new Timestamp(System.currentTimeMillis());
        Timestamp processedDate = new Timestamp(System.currentTimeMillis());
        String nextBookmark = "nextBookmark";
        String auditData = "audit";
        InformTransactionRecord record = new InformTransactionRecord(trial, bookmark, fetchDate, processedDate, nextBookmark, auditData);
        String site = "site";
        store.saveSiteTransaction(site, record);
    }

    @Test
    public void testRepeatSave() throws Exception {
        String trial = "trial";
        String bookmark = "bookmark";
        Timestamp fetchDate = new Timestamp(System.currentTimeMillis());
        Timestamp processedDate = new Timestamp(System.currentTimeMillis());
        String nextBookmark = "nextBookmark";
        String auditData = "audit";
        InformTransactionRecord record = new InformTransactionRecord(trial, bookmark, fetchDate, processedDate, nextBookmark, auditData);
        String site = "site";
        store.saveSiteTransaction(site, record);
        store.saveSiteTransaction(site, record);
        assertTrue(store.isSiteTransactionComplete(site, record));
    }
}
