package edu.chip.carranet.transactionstore;

import edu.chip.carranet.carradatapipeline.transactionstore.InformTransactionRecord;
import edu.chip.carranet.carradatapipeline.transactionstore.InformTransactionDBStore;
import edu.chip.carranet.carradatapipeline.transactionstore.InformTransactionStore;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

/**
 * @author Justin Quan
 * Date: 3/2/11
 */
public class InformTransactionStoreTest {
    private final static String TABLE_NAME = "bookmarktable";
    private final static String USERNAME = "";
    private final static String PASSWORD = "";

    private InformTransactionStore store;
    private Connection connection;

    @BeforeClass
    public static void staticSetup() throws Exception {
        Class.forName("org.h2.Driver");
    }

    @Before
    public void setUp() throws Exception {


        String connectionString = "jdbc:h2:mem:" + "testDB" + ";DB_CLOSE_DELAY=-1";
        connection = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
        InformTransactionDBStore.createTable(connection, TABLE_NAME);

        store = new InformTransactionDBStore(connectionString, USERNAME, PASSWORD, TABLE_NAME);
    }

    @After
    public void tearDown() throws Exception {
        connection.createStatement().execute("DROP TABLE " + TABLE_NAME);
    }

    @Test
    public void testHappyBookmarkBehavior() throws Exception {
        String trial = "trial";
        String bookmark = "bookmark";
        long fakeTime = System.currentTimeMillis();
        Timestamp fetchDate = new Timestamp(fakeTime++);
        Timestamp processedDate = new Timestamp(fakeTime++);
        String nextBookmark = "nextBookmark";
        String auditData = "audit";


        // nothing in the table yet, assert that our bookmark is null
        assertEquals(InformTransactionRecord.BOOKMARK_START, store.loadLastBookmark(trial));

        // stick in a bookmark, make sure the latest is updated
        InformTransactionRecord informTransactionRecord = new InformTransactionRecord(trial, bookmark, fetchDate, processedDate, nextBookmark, auditData);
        store.save(informTransactionRecord);
        assertEquals(nextBookmark, store.loadLastBookmark(trial));

        // and another bookmark
        String nextBookmark2 = "nextBookmark2";
        informTransactionRecord = new InformTransactionRecord(trial, nextBookmark, new Timestamp(fakeTime++), new Timestamp(fakeTime++), nextBookmark2, auditData);
        store.save(informTransactionRecord);
        assertEquals(nextBookmark2, store.loadLastBookmark(trial));

        // add a bookmark retroactively to ensure that nothing is changed
        String nextBookmark3 = "nextBookmark3";
        informTransactionRecord = new InformTransactionRecord(trial, nextBookmark2, new Timestamp(fakeTime-1000*3600), new Timestamp(fakeTime-1000*3600), nextBookmark3, auditData);
        store.save(informTransactionRecord);
        assertEquals(nextBookmark2, store.loadLastBookmark(trial));
    }
}
