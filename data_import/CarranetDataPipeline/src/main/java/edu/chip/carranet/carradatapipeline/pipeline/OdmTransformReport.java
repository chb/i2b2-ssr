package edu.chip.carranet.carradatapipeline.pipeline;

import edu.chip.carranet.importpipeline.process.ProcessException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author Justin Quan
 *
 * Simple xml parser of the xslt report output.  it counts the problem parts of the report,
 * and will tell you if you've got issues.
 */
public class OdmTransformReport {
    private int numUnmapped;
    private int numTruncated;

    public OdmTransformReport(String xml) throws ProcessException {
        //get a factory
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {
            SAXParser sp = spf.newSAXParser();
            ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
            sp.parse(bais, new ReportParser());
        } catch (ParserConfigurationException e) {
            throw new ProcessException(e);
        } catch (SAXException e) {
            throw new ProcessException(e);
        } catch (IOException e) {
            throw new ProcessException(e);
        }
    }

    private class ReportParser extends DefaultHandler {
        private ReportParser() {
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {

            if(qName.equalsIgnoreCase("ITEM_NOT_MAPPED")) {
                ++OdmTransformReport.this.numUnmapped;
            } else if(qName.equalsIgnoreCase("VALUES_TRUNCATED")) {
                ++OdmTransformReport.this.numTruncated;
            }
        }
    }

    public boolean hasProblem() {
        return this.numUnmapped > 0;
    }
}
