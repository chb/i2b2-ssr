package edu.chip.carranet.carradatapipeline;

import edu.chip.carranet.carradatapipeline.pipeline.FetchFromInform;
import edu.chip.carranet.carradatapipeline.pipeline.InformI2b2Processor;
import edu.chip.carranet.carradatapipeline.pipeline.OdmIgnoreList;
import edu.chip.carranet.carradatapipeline.pipeline.SqlOutputter;
import edu.chip.carranet.carradatapipeline.sitemap.ISiteMap;
import edu.chip.carranet.carradatapipeline.transactionstore.InformTransactionDBStore;
import edu.chip.carranet.carradatapipeline.transactionstore.InformTransactionStore;
import edu.chip.carranet.carradatapipeline.transactionstore.SiteTransactionDBStore;
import edu.chip.carranet.carradatapipeline.transactionstore.SiteTransactionStore;
import edu.chip.carranet.importpipeline.*;
import edu.chip.carranet.importpipeline.config.FetchConfig;
import edu.chip.carranet.importpipeline.dispatch.Dispatchable;
import edu.chip.carranet.importpipeline.fetch.Fetcher;
import edu.chip.carranet.importpipeline.output.Outputter;
import edu.chip.carranet.importpipeline.process.Processor;
import edu.chip.carranet.sitemap.MockSiteMap;
import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;

/**
 * @author Justin Quan
 * @link http://chip.org Date: 3/3/11
 */
public class PipelineTest {
    private String connectionString = "jdbc:oracle:thin:@chdev1:1521/CHRACDEV";
    private String userName = "rc_carra_vm004";
    private String password = "chracdev";
    private final String TRANSACTION_TABLE_NAME = "JUNIT_TRANSACTIONTABLE";
    private final String SITE_TABLE_NAME = "JUNIT_SITETABLE";

    @BeforeClass
    public static void staticSetup() throws Exception {


    }

    @Before
    public void setUp() throws Exception {
        BasicConfigurator.configure();
    }

    @After
    public void tearDown() throws Exception {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void testHappyPipeline() throws Exception {
        // create in memory db for FetchFromInform
        Connection connection = DriverManager.getConnection(connectionString, userName, password);
        connection.setAutoCommit(false);

        // init tables
        InformTransactionDBStore.createTable(connection, TRANSACTION_TABLE_NAME);
        InformTransactionStore store = new InformTransactionDBStore(connection, TRANSACTION_TABLE_NAME);
        SiteTransactionDBStore.createTable(connection, SITE_TABLE_NAME);
        SiteTransactionStore siteStore = new SiteTransactionDBStore(connectionString, userName, password, SITE_TABLE_NAME);

        // build up a mock inform client from deidentified edu.chip.carranet.data
        List<File> files = new ArrayList<File>();
        files.add(new File(getClass().getClassLoader().getResource("getTrans1.xml").toURI()));
        files.add(new File(getClass().getClassLoader().getResource("getTrans2.xml").toURI()));
//        for(int i = 0; i < 67; ++i) {
//            List<Integer> skip = Arrays.asList(new Integer[]{2, 4, 5, 22, 27, 34, 41, 42, 46, 53, 54, 55, 66});
//            if(skip.contains(i)) {
//                continue;
//            }
//            // filenames start at 1, offset by one
//            files.add(new File("/home/justin/Archive5/transaction-" + (i+1) + ".xml"));
//        }
        MockInformClient mockInformClient = new MockInformClient(files);

        // latches to ensure our processors get run the minimum number of times
        CountDownLatch fetcherLatch = new CountDownLatch(1);
        CountDownLatch processorLatch = new CountDownLatch(1);
        CountDownLatch outputterLatch =  new CountDownLatch(files.size());

        FetchFromInform fetchFromInform = new FetchFromInform(mockInformClient, "someTrial",
                FetchConfig.DEFAULT_FETCH_CONFIG, store);
        Fetcher fetcher = new DelegatingTestFetcher(fetchFromInform, fetcherLatch);

        File formIgnoreList = new File(getClass().getClassLoader().getResource("odmFormIgnoreList.txt").toURI());
        File itemIgnoreList = new File(getClass().getClassLoader().getResource("odmItemIgnoreList.txt").toURI());
        OdmIgnoreList odmIgnoreList = new OdmIgnoreList(formIgnoreList, itemIgnoreList);
        Processor processor = new DelegatingTestProcessor(
                new InformI2b2Processor(odmIgnoreList, connectionString, userName, password),
                processorLatch);


        List<MockSiteMap.MockConnectionInfo> connectionsList = new ArrayList<MockSiteMap.MockConnectionInfo>();
        connectionsList.add(new MockSiteMap.MockConnectionInfo(connectionString, "rc_carra_vm002", password));
        connectionsList.add(new MockSiteMap.MockConnectionInfo(connectionString, "rc_carra_vm003", password));
        connectionsList.add(new MockSiteMap.MockConnectionInfo(connectionString, "rc_carra_vm004", password));

        Map<String, MockSiteMap.MockConnectionInfo> siteConnectionMap = new HashMap<String, MockSiteMap.MockConnectionInfo>();
        for(int i = 0; i < 20; ++i) {
            siteConnectionMap.put(""+i, connectionsList.get(i%connectionsList.size()));
        }
        siteConnectionMap.put("15186", connectionsList.get(0));
        siteConnectionMap.put("15212", connectionsList.get(1));
        ISiteMap siteMap = new MockSiteMap(siteConnectionMap);

        try {
            SqlOutputter sqlOutputter = new SqlOutputter(fetchFromInform, siteStore, siteMap);
            sqlOutputter.setDoBatch(true);
            Outputter outputter = new DelegatingTestOutputter(sqlOutputter, outputterLatch);

            Map<Dispatchable.DispatchType, Processor> processorMap = new HashMap<Dispatchable.DispatchType, Processor>();
            processorMap.put(Dispatchable.DispatchType.ODMData, processor);
            Map<Dispatchable.DispatchType, Outputter> outputterMap = new HashMap<Dispatchable.DispatchType, Outputter>();
            outputterMap.put(Dispatchable.DispatchType.SQLCommands, outputter);
            final IngestionEngine engine = new IngestionEngine(Collections.singletonList(fetcher),
                    processorMap, outputterMap);

            engine.start();

            // wait around and check for errors
            while(fetcherLatch.getCount() != 0 || processorLatch.getCount() != 0 || outputterLatch.getCount() != 0) {
                fetcherLatch.await(1, TimeUnit.SECONDS);
                processorLatch.await(1, TimeUnit.SECONDS);
                outputterLatch.await(1, TimeUnit.SECONDS);
                ((LatchedThrowableCollector) fetcher).assertOK();
                ((LatchedThrowableCollector) processor).assertOK();
                ((LatchedThrowableCollector)outputter).assertOK();
            }

            fetcherLatch.await(20000, TimeUnit.SECONDS);
            assertEquals(0, fetcherLatch.getCount());
            processorLatch.await(20000, TimeUnit.SECONDS);
            assertEquals(0, processorLatch.getCount());
            outputterLatch.await(20000, TimeUnit.SECONDS);
            assertEquals(0, outputterLatch.getCount());
            engine.stop();
            engine.getShutDownLatch().await(20000,TimeUnit.SECONDS);
            assertEquals(0, engine.getShutDownLatch().getCount());

            ((LatchedThrowableCollector) fetcher).assertOK();
            ((LatchedThrowableCollector) processor).assertOK();
            ((LatchedThrowableCollector)outputter).assertOK();

        } finally {
            dropTable(connection, TRANSACTION_TABLE_NAME);
            dropTable(connection, SITE_TABLE_NAME);
        }
    }

    private static void dropTable(Connection connection, String tablename) throws SQLException {
        connection.createStatement().executeQuery("DROP TABLE " + tablename);
    }

    // TODO
    public void testEmptyTransactionResult() throws Exception {

    }

}

