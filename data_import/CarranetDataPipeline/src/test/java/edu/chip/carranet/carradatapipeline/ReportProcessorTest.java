package edu.chip.carranet.carradatapipeline;

import edu.chip.carranet.carradatapipeline.pipeline.OdmTransformReport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static junit.framework.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: Justin
 * Date: 5/22/11
 * Time: 1:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReportProcessorTest {
    private String reportXml;
    private OdmTransformReport parser;

    @Before
    public void setUp() throws Exception {
        // load test file
        File f = new File(this.getClass().getClassLoader().getResource("report.xml").toURI());
        BufferedReader br = new BufferedReader(new FileReader(f));
        StringBuilder sb = new StringBuilder();
        String line;
        while( (line = br.readLine()) != null ) {
            sb.append(line);
        }
        this.reportXml = sb.toString();

        parser = new OdmTransformReport(reportXml);
    }

    @After
    public void tearDown() throws Exception {
        //To change body of created methods use File | Settings | File Templates.
    }

    @Test
    public void testGetTruncated() throws Exception {
        assertTrue(parser.hasProblem());
    }
}
