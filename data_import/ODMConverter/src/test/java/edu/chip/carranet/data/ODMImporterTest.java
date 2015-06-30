package edu.chip.carranet.data;

import org.junit.*;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;

import static org.junit.Assert.*;

@Ignore
public class ODMImporterTest {
    private final static String connectionString = "jdbc:oracle:thin:@oradev3:1521/CHRACDEV";
    private final static String userName = "RC_CARRA_VM1_PROJECT1";
    private final static String password = "chracdev";
    private final static String[] tables = new String[]{"observation_fact", "visit_dimension", "patient_dimension", "TBLRSLTDATAIMPORT", "tmp_EncTable"};
    private static boolean doDrop;

    private Connection connection;


    @BeforeClass
    public static void staticSetup() throws Exception {
        Connection c = DriverManager.getConnection(connectionString, userName, password);
        c.setAutoCommit(false);
        abortIfTablesExist(c, tables);
        createImportTables(c);
        createI2b2Tables(c);
        c.commit();
        doDrop = true;
    }

    @Before
    public void setUp() throws Exception {
        connection = DriverManager.getConnection(connectionString, userName, password);
        connection.setAutoCommit(false);
    }

    @AfterClass
    public static void staticTearDown() throws Exception {
        if(doDrop) {
            Connection c = DriverManager.getConnection(connectionString, userName, password);
            c.setAutoCommit(false);
            for(String table : tables) {
                c.createStatement().execute("drop table " + table);
                c.commit();
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        for(String table : tables) {
            connection.createStatement().execute("truncate table " + table);
        }
    }


    @Test
    public void testBadODMImport() throws Exception {
        final ODMImporter importer = new ODMImporter(this.connection);
        final FileInputStream fis = new FileInputStream(new File(getClass().getClassLoader().getResource("with-bad-characters.xml").toURI()));
        final String sql = importer.getReport(fis);

        /**
         * Yeah, sorry, no automated tests here because they're not possible. Please run the above
         * code, setting a breakpoint on the line gets the sqlStatements in ODMImport.processData()
         * and take a look at the SQL. You should see the semicolons changed to commas and a pair
         * of single quotes where there used to be a single one.
         */

    }


    @Test
    public void testHappyCase() throws Exception {
        processFile("getTrans1.xml");

        ResultSet rs = connection.createStatement().executeQuery("select * from OBSERVATION_FACT");
        assertTrue(rs.next() && !rs.getRowId("concept_cd").toString().isEmpty());
    }

    @Test(expected = SQLIntegrityConstraintViolationException.class)
    public void testDupeEntry() throws Exception {
        processFile("test_dupe_bday.xml");
    }

    @Test
    public void testEmptySourceSystemId() throws Exception {
        processFile("demographics.xml");

        String[] sourceTables = new String[]{"observation_fact", "visit_dimension", "patient_dimension"};

        for(String table : sourceTables) {
            ResultSet rs = connection.createStatement().executeQuery("select sourcesystem_cd from " + table);
            boolean hasResults = false;
            while(rs.next()) {
                hasResults = true;
                assertEquals("CARRANET", rs.getString("sourcesystem_cd"));
            }
            assertTrue(hasResults);
        }

    }

    @Test
    public void testEmptyAgeInYears() throws Exception {
        processFile("demographics.xml");

        ResultSet rs = connection.createStatement().executeQuery("select AGE_IN_YEARS_NUM from patient_dimension");

        boolean hasResults = false;
        while(rs.next()) {
            hasResults = true;
            assertNull(rs.getString("AGE_IN_YEARS_NUM"));
        }
        assertTrue(hasResults);
    }

    @Test
    public void testNullVisitStartDate() throws Exception {
        processFile("demographics.xml");

        ResultSet rs = connection.createStatement().executeQuery("select START_DATE from visit_dimension");

        boolean hasResults = false;
        while(rs.next()) {
            hasResults = true;
            assertNull(rs.getString("START_DATE"));
        }
        assertTrue(hasResults);
    }

    @Test
    public void testVisitStartDate() throws Exception {
        processFile("visit.xml");


        ResultSet rs = connection.createStatement().executeQuery("select START_DATE from visit_dimension");

        assertTrue(rs.next());
        Date d = rs.getDate("START_DATE");
        Date expected = Date.valueOf("2010-07-07");
        assertEquals(expected.getTime(), d.getTime());

        assertTrue(rs.next());
        d = rs.getDate("START_DATE");
        expected = Date.valueOf("2010-09-05");
        assertEquals(expected.getTime(), d.getTime());

        // process a piece of xml that updates the enc date
        processFile("updated_visit.xml");
        rs = connection.createStatement().executeQuery("select START_DATE from visit_dimension");

        assertTrue(rs.next());
        d = rs.getDate("START_DATE");
        expected = Date.valueOf("2011-07-07");
        assertEquals(expected.getTime(), d.getTime());

        // process a piece of xml that doesn't update the enc date
        processFile("updated_non_visit.xml");
        rs = connection.createStatement().executeQuery("select START_DATE from visit_dimension");

        assertTrue(rs.next());
        d = rs.getDate("START_DATE");
        expected = Date.valueOf("2011-07-07");
        assertEquals(expected.getTime(), d.getTime());
    }

    @Test
    public void testSimpleInsert() throws Exception {
        processFile("simple_insert.xml");
        ResultSet rs = connection.createStatement().executeQuery("select * from observation_fact");
        assertTrue(rs.next());
        assertEquals("carranet|registry1|v1:915", rs.getString("concept_cd"));
        assertEquals("@", rs.getString("modifier_cd"));
        assertEquals("@", rs.getString("valtype_cd"));
        assertNull(rs.getString("tval_char"));
        assertNull(rs.getString("nval_num"));
    }

    @Test
    public void testSimpleUpdate() throws Exception {
        processFile("simple_update1.xml");
        ResultSet rs = connection.createStatement().executeQuery("select * from observation_fact");
        assertTrue(rs.next());
        assertEquals("carranet|registry1|v1:157", rs.getString("concept_cd"));
        assertFalse(rs.next());

        processFile("simple_update2.xml");
        rs = connection.createStatement().executeQuery("select * from observation_fact");
        assertTrue(rs.next());
        assertEquals("carranet|registry1|v1:153", rs.getString("concept_cd"));
        assertFalse(rs.next());


    }

    @Test
    public void testSimpleTvalInsert() throws Exception {
        processFile("simple_tval_insert.xml");
        ResultSet rs = connection.createStatement().executeQuery("select * from observation_fact");
        assertTrue(rs.next());
        assertEquals("carranet|registry1|v1:172", rs.getString("concept_cd"));
        assertEquals("@", rs.getString("modifier_cd"));
        assertEquals("T", rs.getString("valtype_cd"));
        assertEquals("justin's magic hot sauce phase 1 trial", rs.getString("tval_char"));
        assertNull(rs.getString("nval_num"));
    }

    @Test
    public void testSimpleNvalInsert() throws Exception {
        processFile("simple_nval_insert.xml");
        ResultSet rs = connection.createStatement().executeQuery("select * from observation_fact");
        assertTrue(rs.next());
        assertEquals("carranet|registry1|v1:1244", rs.getString("concept_cd"));
        assertEquals("@", rs.getString("modifier_cd"));
        assertEquals("N", rs.getString("valtype_cd"));
        assertEquals("E", rs.getString("tval_char"));
        assertEquals(1.1f, rs.getFloat("nval_num"), 0);

    }

    @Test
    public void testNonNumericNvalInsert() throws Exception {
        processFile("non_numeric_nval_insert.xml");
        ResultSet rs = connection.createStatement().executeQuery("select * from observation_fact");
        assertTrue(rs.next());
        assertEquals("carranet|registry1|v1:1244", rs.getString("concept_cd"));
        assertEquals("@", rs.getString("modifier_cd"));
        assertEquals("N", rs.getString("valtype_cd"));
        assertEquals("E", rs.getString("tval_char"));
        // it's actually checking for null...
        assertEquals(0f, rs.getFloat("nval_num"), 0);

    }

    @Test
    public void testSimpleTvalModifierInsert() throws Exception {
        processFile("simple_tval_modifier_insert.xml");
        ResultSet rs = connection.createStatement().executeQuery("select * from observation_fact");
        assertTrue(rs.next());
        assertEquals("carranet|registry1|v1:379", rs.getString("concept_cd"));
        assertEquals("@", rs.getString("modifier_cd"));
        assertEquals("T", rs.getString("valtype_cd"));
        assertEquals("hot sauce", rs.getString("tval_char"));
        assertNull(rs.getString("nval_num"));
    }

    @Test
    public void testSimpleTvalModifierRepeatKeyInsert() throws Exception {
        processFile("simple_tval_modifier_repeat_insert.xml");
        ResultSet rs = connection.createStatement().executeQuery("select * from observation_fact");
        assertTrue(rs.next());
        assertEquals("carranet|registry1|v1:628", rs.getString("concept_cd"));
        assertEquals("300212026627073", rs.getString("modifier_cd"));
        assertEquals("T", rs.getString("valtype_cd"));
        assertEquals("2003-01--T-:-:-+00:00", rs.getString("tval_char"));
        assertNull(rs.getString("nval_num"));
    }

    @Test
    public void testSimpleModifierRepeatKeyInsert() throws Exception {
        processFile("simple_modifier_repeat_insert.xml");
        ResultSet rs = connection.createStatement().executeQuery("select * from observation_fact");
        assertTrue(rs.next());
        assertEquals("carranet|registry1|v1:643", rs.getString("concept_cd"));
        assertEquals("300212026627073", rs.getString("modifier_cd"));
        assertEquals("@", rs.getString("valtype_cd"));
        assertNull(rs.getString("tval_char"));
        assertNull(rs.getString("nval_num"));
    }

    @Test
    public void testDeletionOfEmptyValueFacts() throws Exception {
        processFile("simple_insert_two.xml");
        ResultSet rs = connection.createStatement().executeQuery("select * from observation_fact");
        assertTrue(rs.next());
        assertTrue(rs.next());
        processFile("simple_delete.xml");
        rs = connection.createStatement().executeQuery("select * from observation_fact");
        assertFalse(rs.next());
    }

    @Test
    public void testSimpleAnnotation() throws Exception {
        processFile("simple_insert.xml");
        processFile("simple_annotation.xml");
        ResultSet rs = connection.createStatement().executeQuery("select * from observation_fact");
        assertTrue(rs.next());
        assertEquals("carranet|registry1|v1:915", rs.getString("concept_cd"));
        assertEquals("@", rs.getString("modifier_cd"));
        assertEquals("@", rs.getString("valtype_cd"));
        assertNull(rs.getString("tval_char"));
        assertNull(rs.getString("nval_num"));
    }

    @Test
    public void testShortDates() throws Exception {
        processFile("short_dates.xml");
        ResultSet rs = connection.createStatement().executeQuery("select * from visit_dimension");

        assertTrue(rs.next());
        Date d = rs.getDate("START_DATE");
        Date expected = Date.valueOf("2010-07-01");
        assertEquals(expected.getTime(), d.getTime());

        assertTrue(rs.next());
        d = rs.getDate("START_DATE");
        expected = Date.valueOf("2010-01-01");
        assertEquals(expected.getTime(), d.getTime());
    }

    @Test
    public void testDemographics() throws Exception {

    }

    private static void abortIfTablesExist(Connection connection, String[] tables) throws SQLException {
        for(String table : tables) {
            if(tableExists(connection, table)) {
                fail("table exists:" + table);
            }
        }
    }

    private void processFile(String filename) throws TransformerException, URISyntaxException, IOException, SQLException {
        ODMImporter odmImporter = new ODMImporter(connection);
        File input = new File(getClass().getClassLoader().getResource(filename).toURI());
        FileInputStream fis = new FileInputStream(input);
        odmImporter.processData(fis);
        fis.close();
    }

    private static boolean tableExists(Connection connection, String tableName) throws SQLException {
        String checkQuery = "SELECT COUNT(*) as RESULT FROM ALL_TABLES WHERE TABLE_NAME = '" + tableName + "'";
        ResultSet resultSet = connection.createStatement().executeQuery(checkQuery);
        return resultSet.next() && resultSet.getInt("RESULT") > 0;
    }

    private static void createImportTables(Connection conn) throws SQLException {
        conn.createStatement().execute(Queries.createImportTableQuery);
        conn.createStatement().execute(Queries.createImportTmpEncTable);
        conn.commit();
    }
    private static void createI2b2Tables(Connection conn) throws SQLException {
        // create i2b2 tables
        String createObs = "CREATE TABLE OBSERVATION_FACT (" +
                "ENCOUNTER_NUM   NUMBER(38,0) NOT NULL," +
                "PATIENT_NUM     NUMBER(38,0) NOT NULL," +
                "CONCEPT_CD      VARCHAR2(500) NOT NULL," +
                "PROVIDER_ID     VARCHAR2(50) NOT NULL," +
                "START_DATE      DATE NOT NULL," +
                "MODIFIER_CD     VARCHAR2(100) NOT NULL," +
                "VALTYPE_CD      VARCHAR2(50) NULL," +
                "TVAL_CHAR       VARCHAR2(255) NULL," +
                "NVAL_NUM        NUMBER(18,5) NULL," +
                "VALUEFLAG_CD    VARCHAR2(50) NULL," +
                "QUANTITY_NUM    NUMBER(18,5) NULL," +
                "INSTANCE_NUM    NUMBER(18,0) NULL," +
                "UNITS_CD        VARCHAR2(50) NULL," +
                "END_DATE        DATE NULL," +
                "LOCATION_CD     VARCHAR2(50) NULL," +
                "CONFIDENCE_NUM  NUMBER(18,5) NULL," +
                "OBSERVATION_BLOB CLOB NULL," +
                "UPDATE_DATE     DATE NULL," +
                "DOWNLOAD_DATE   DATE NULL," +
                "IMPORT_DATE     DATE NULL," +
                "SOURCESYSTEM_CD VARCHAR2(50) NULL," +
                "UPLOAD_ID       NUMBER(38,0) NULL," +
                "    CONSTRAINT OBSERVATION_FACT_PK PRIMARY KEY(ENCOUNTER_NUM,CONCEPT_CD,PROVIDER_ID,START_DATE,MODIFIER_CD)" +
                ")";
        String createVisit = "CREATE TABLE VISIT_DIMENSION ( \n" +
                "\tENCOUNTER_NUM       NUMBER(38,0) NOT NULL,\n" +
                "\tPATIENT_NUM         NUMBER(38,0) NOT NULL,\n" +
                "    ACTIVE_STATUS_CD    VARCHAR2(50) NULL,\n" +
                "\tSTART_DATE          DATE NULL,\n" +
                "\tEND_DATE            DATE NULL,\n" +
                "\tINOUT_CD            VARCHAR2(50) NULL,\n" +
                "\tLOCATION_CD         VARCHAR2(50) NULL,\n" +
                "\tLOCATION_PATH  \t    VARCHAR2(900) NULL,\n" +
                "\tVISIT_BLOB      \tCLOB NULL,\n" +
                "\tUPDATE_DATE         DATE NULL,\n" +
                "\tDOWNLOAD_DATE       DATE NULL,\n" +
                "\tIMPORT_DATE         DATE NULL,\n" +
                "\tSOURCESYSTEM_CD     VARCHAR2(50) NULL,\n" +
                "\tUPLOAD_ID       \tNUMBER(38,0) NULL , \n" +
                "    CONSTRAINT  VISIT_DIMENSION_PK PRIMARY KEY(ENCOUNTER_NUM,PATIENT_NUM)\n" +
                ")";

        String createPat = "CREATE TABLE PATIENT_DIMENSION ( \n" +
                "\tPATIENT_NUM      \tNUMBER(38,0) NOT NULL,\n" +
                "\tVITAL_STATUS_CD  \tVARCHAR2(50) NULL,\n" +
                "\tBIRTH_DATE       \tDATE NULL,\n" +
                "\tDEATH_DATE       \tDATE NULL,\n" +
                "\tSEX_CD           \tVARCHAR2(50) NULL,\n" +
                "\tAGE_IN_YEARS_NUM \tNUMBER(38,0) NULL,\n" +
                "\tLANGUAGE_CD      \tVARCHAR2(50) NULL,\n" +
                "\tRACE_CD          \tVARCHAR2(50) NULL,\n" +
                "\tMARITAL_STATUS_CD\tVARCHAR2(50) NULL,\n" +
                "\tRELIGION_CD      \tVARCHAR2(50) NULL,\n" +
                "\tZIP_CD           \tVARCHAR2(10) NULL,\n" +
                "\tSTATECITYZIP_PATH\tVARCHAR2(700) NULL,\n" +
                "\tPATIENT_BLOB     \tCLOB NULL,\n" +
                "\tUPDATE_DATE      \tDATE NULL,\n" +
                "\tDOWNLOAD_DATE    \tDATE NULL,\n" +
                "\tIMPORT_DATE      \tDATE NULL,\n" +
                "\tSOURCESYSTEM_CD  \tVARCHAR2(50) NULL,\n" +
                "\tUPLOAD_ID        \tNUMBER(38,0) NULL ,\n" +
                "    CONSTRAINT PATIENT_DIMENSION_PK PRIMARY KEY(PATIENT_NUM)\n" +
                ")";

        conn.createStatement().execute(createObs);
        conn.createStatement().execute(createVisit);
        conn.createStatement().execute(createPat);
        conn.commit();

    }

}
