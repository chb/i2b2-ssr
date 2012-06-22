package edu.chip.carranet.carradatapipeline;

import edu.chip.carranet.carradatapipeline.pipeline.*;
import edu.chip.carranet.carradatapipeline.sitemap.ISiteMap;
import edu.chip.carranet.carradatapipeline.transactionstore.InformTransactionDBStore;
import edu.chip.carranet.carradatapipeline.transactionstore.InformTransactionStore;
import edu.chip.carranet.carradatapipeline.transactionstore.SiteTransactionDBStore;
import edu.chip.carranet.carradatapipeline.transactionstore.SiteTransactionStore;
import edu.chip.carranet.importpipeline.config.FetchConfig;
import edu.chip.carranet.sitemap.MockSiteMap;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Justin
 * Date: 5/30/11
 * Time: 1:37 AM
 * To change this template use File | Settings | File Templates.
 */
@Ignore
public class SqlOutputterTest {
    private String connectionString = "jdbc:oracle:thin:@chdev1:1521/CHRACDEV";
    private String userName = "rc_carra_vm004";
    private String password = "chracdev";
    private final String TRANSACTION_TABLE_NAME = "JUNIT_TRANSACTIONTABLE";
    private final String SITE_TABLE_NAME = "JUNIT_SITETABLE";


    /**
     * This test will ensure that we resume outputting at the appropriate place
     * based on what we find in the site transaction table.  There will be two facts
     * we attempt to process, the first of which should throw an output error, but we'll
     * preload the site transaction table so we skip it to avoid failure.
     *
     * @throws Exception
     */
    @Test
    public void testResumeSite() throws Exception {
        Connection connection = DriverManager.getConnection(connectionString, userName, password);
        connection.setAutoCommit(false);
        try {
            // init tables
            InformTransactionDBStore.createTable(connection, TRANSACTION_TABLE_NAME);
            InformTransactionStore store = new InformTransactionDBStore(connection, TRANSACTION_TABLE_NAME);
            SiteTransactionDBStore.createTable(connection, SITE_TABLE_NAME);
            SiteTransactionStore siteStore = new SiteTransactionDBStore(connectionString, userName, password, SITE_TABLE_NAME);

            List<File> files = new ArrayList<File>();
            files.add(new File(getClass().getClassLoader().getResource("SqlOutputterTest_ResumeSite.xml").toURI()));

            MockInformClient mockInformClient = new MockInformClient(files);
            FetchFromInform fetchFromInform = new FetchFromInform(mockInformClient, "someTrial",
                    FetchConfig.DEFAULT_FETCH_CONFIG, store);

            File formIgnoreList = new File(getClass().getClassLoader().getResource("odmFormIgnoreList.txt").toURI());
            File itemIgnoreList = new File(getClass().getClassLoader().getResource("odmItemIgnoreList.txt").toURI());
            OdmIgnoreList odmIgnoreList = new OdmIgnoreList(formIgnoreList, itemIgnoreList);
            InformI2b2Processor processor = new InformI2b2Processor(odmIgnoreList, connectionString, userName, password, false);

            List<MockSiteMap.MockConnectionInfo> connectionsList = new ArrayList<MockSiteMap.MockConnectionInfo>();
            connectionsList.add(new MockSiteMap.MockConnectionInfo(connectionString, "rc_carra_vm002", password));
            connectionsList.add(new MockSiteMap.MockConnectionInfo(connectionString, "rc_carra_vm003", password));

            Map<String, MockSiteMap.MockConnectionInfo> siteConnectionMap = new HashMap<String, MockSiteMap.MockConnectionInfo>();
            siteConnectionMap.put("site1", connectionsList.get(0));
            siteConnectionMap.put("site2", connectionsList.get(1));
            ISiteMap siteMap = new MockSiteMap(siteConnectionMap);

            SqlOutputter sqlOutputter = new SqlOutputter(fetchFromInform, siteStore, siteMap);

            InformODMData informData = fetchFromInform.fetch();
            SqlCommands sqlCommands = processor.process(informData);

            //  preload siteTransactionTable
            siteStore.saveSiteTransaction("site1", informData.getTransactionRecord());

            // pre count site 1
            Connection con = connectionsList.get(0).makeConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from observation_fact where patient_num = '7777'");
            int facts = 0;
            while(rs.next()) { facts++;}

            sqlOutputter.output(sqlCommands);

            // post count and validate
            rs = con.createStatement().executeQuery("select * from observation_fact where patient_num = '7777'");
            int newFacts = 0;
            while(rs.next()) { newFacts++;}
            assertEquals(facts, newFacts);

        } finally {
            // cleanup
            dropTable(connection, TRANSACTION_TABLE_NAME);
            dropTable(connection, SITE_TABLE_NAME);
        }
    }

    private static void dropTable(Connection connection, String tablename) throws SQLException {
        connection.createStatement().executeQuery("DROP TABLE " + tablename);
    }
}
