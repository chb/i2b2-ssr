package edu.chip.carranet.carradatapipeline;

import edu.chip.carranet.carradatapipeline.pipeline.FetchFromInform;
import edu.chip.carranet.carradatapipeline.pipeline.InformODMData;
import edu.chip.carranet.carradatapipeline.transactionstore.InformTransactionDBStore;
import edu.chip.carranet.carradatapipeline.transactionstore.InformTransactionStore;
import edu.chip.carranet.importpipeline.config.FetchConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * @author Justin Quan
 * @link http://chip.org Date: 7/5/11
 */
public class FetchFromInformTest {
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
        connection.commit();
    }

    @Test
    public void testEndOfBookmark() throws Exception {
        List<File> files = new ArrayList<File>();
        files.add(new File(getClass().getClassLoader().getResource("fetch/testEndOfBookmark1.xml").toURI()));
        files.add(new File(getClass().getClassLoader().getResource("fetch/testEndOfBookmark2.xml").toURI()));
        MockInformClient informClient = new MockInformClient(files);
        FetchFromInform fetchFromInform =
                new FetchFromInform(informClient, "trial", FetchConfig.DEFAULT_FETCH_CONFIG, store);

        // fetch twice, first time for results, second time the end of the result set
        InformODMData data = fetchFromInform.fetch();
        assertNotNull(data);
        data.getTransactionRecord().setProcessedDate(new Timestamp(System.currentTimeMillis()));
        fetchFromInform.notifyProcessed(data.getTransactionRecord());
        assertNull(fetchFromInform.fetch());

    }


}
