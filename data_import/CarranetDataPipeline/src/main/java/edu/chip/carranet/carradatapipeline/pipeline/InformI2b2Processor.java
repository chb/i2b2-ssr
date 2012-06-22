package edu.chip.carranet.carradatapipeline.pipeline;

import edu.chip.carranet.carradatapipeline.PipelineConfig;
import edu.chip.carranet.data.ODMImporter;
import edu.chip.carranet.data.ODMXSLT;
import edu.chip.carranet.importpipeline.process.ProcessException;
import edu.chip.carranet.importpipeline.process.Processor;
import edu.chip.carranet.inform.InformJAXBContext;
import org.apache.log4j.Logger;
import org.cdisc.ns.odm.v1.*;
import org.jvnet.jaxb2_commons.lang.builder.JAXBToStringBuilder;
import org.spin.tools.JAXBUtils;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.util.JAXBResult;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: justinquan
 * Date: Oct 22, 2010
 * Time: 3:37:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class InformI2b2Processor implements Processor<InformODMData, SqlCommands> {
    private static final Logger log = Logger.getLogger(InformI2b2Processor.class);
    private ODMXSLT odmxslt;
    private ODMImporter odmImporter;
    private OdmIgnoreList ignoreLists;
    private boolean ignoreUmappedFacts;

    public InformI2b2Processor(OdmIgnoreList ignoreLists, String connectionString, String dbUser, String dbPass, boolean ignoreUnmappedFacts) throws TransformerConfigurationException, SQLException {
        this.odmxslt = new ODMXSLT();
        Connection connection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        this.odmImporter = new ODMImporter(connection);
        this.ignoreLists = ignoreLists;
        this.ignoreUmappedFacts = ignoreUnmappedFacts;
    }

    @Override
    public SqlCommands process(InformODMData data) throws ProcessException {
        log.info("processing inform data");
        try {
            Map<String,String> siteSqlMap = new HashMap<String, String>();
            for(String site : data.getSiteData().keySet()) {
                StringBuilder processSqlBuilder = new StringBuilder();
                for(ClinicalData siteDataPoint : data.getSiteData().get(site)) {
                    pruneIgnoredTerms(siteDataPoint);
                    String xml = marshalToString(siteDataPoint);
                    ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
                    processSqlBuilder.append(odmxslt.transformToProcessSql(bais));
                }
                siteSqlMap.put(site, processSqlBuilder.toString());
            }

            // update audit
            String xml = marshalToString(data.getOdm());
            ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
            String report = odmImporter.getReport(bais);
            data.getTransactionRecord().setAuditReport(report);
            OdmTransformReport reporter = new OdmTransformReport(report);
            if(reporter.hasProblem()) {
                if(ignoreUmappedFacts){
                    log.fatal("WARNING: Unmapped facts found report, configured to continue anyway " + report);
                }
                else{
                    throw new ProcessException("Found unmapped facts in report:" + report);
                }
            }

            return new SqlCommands(siteSqlMap, data.getTransactionRecord());
        } catch (JAXBException e) {
            throw new ProcessException(e);
        } catch (TransformerException e) {
            throw new ProcessException(e);
        } catch (SQLException e) {
            throw new ProcessException(e);
        }
    }

    // holy nested forloops batman!
    private ClinicalData pruneIgnoredTerms(ClinicalData clinicalData) {
        for(SubjectData subjectData : clinicalData.getSubjectData()) {
            for(StudyEventData studyEventData : subjectData.getStudyEventData()) {
                Iterator<FormData> formDataIterator = studyEventData.getFormData().iterator();
                while(formDataIterator.hasNext()) {
                    FormData formData = formDataIterator.next();
                    if(ignoreLists.getFormIgnoreList().contains(formData.getFormOID())) {
                        formDataIterator.remove();
                        log.info("pruned form: " + formData.getFormOID());
                        continue;
                    }
                    for(ItemGroupData itemGroupData : formData.getItemGroupData()) {
                        Iterator<ItemData> itemDataIterator = itemGroupData.getItemData().iterator();
                        while(itemDataIterator.hasNext()) {
                            ItemData itemData = itemDataIterator.next();
                            if(ignoreLists.getItemIgnoreList().contains(itemData.getItemOID())) {
                                itemDataIterator.remove();
                                log.info("pruned item: " + itemData.getItemOID());
                            }
                        }
                    }
                }
            }
        }
        return clinicalData;
    }

    private void checkReport(String xml) throws ProcessException {
        // parse out errors
        String errors = "";

        // policy section
        if(!errors.isEmpty()) {
            // we really shouldn't continue
            throw new ProcessException("Aborting due to errors in report:" + errors);
        }


    }

    private static String marshalToString(Object o) throws JAXBException {
        Marshaller m = JAXBUtils.getMarshaller(InformJAXBContext.getInstance().getPackageList());
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        m.marshal(o, sw);
        return sw.toString();
    }
}

