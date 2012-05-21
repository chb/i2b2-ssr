package edu.chip.carranet.carradatapipeline.pipeline.obfuscation;

import com.phaseforward.informadapter.odm._1.GetTransactionsResult;
import com.phaseforward.informadapter.odm._2.ResponseODM;
import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.obfuscators.PatientObfuscator;
import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.obfuscators.ReasonForChangeObfuscator;
import edu.chip.carranet.inform.InformClient;
import edu.chip.carranet.inform.InformClientAPI;
import edu.chip.carranet.inform.InformJAXBContext;
import org.apache.log4j.Logger;
import org.cdisc.ns.odm.v1.*;
import org.joda.time.DateTime;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.security.SecureRandom;
import java.util.*;

/**
 * Deidentify the follow items
 * 1. site, which is currently a 3-digit site number plus a 4 or 5 digit site-specific sequential integer, with a random identifier.
 * 2. subject, which is a 5-digit number
 * 3. date of birth with a random date, but consistent within same-subject records
 * 4. zip code with random number.
 *
 * @author Justin Quan
 * @link http://chip.org
 * Date: 3/16/11
 */
public class ODMDeidentifyProcessor {
    private static final Logger log = Logger.getLogger(ODMDeidentifyProcessor.class);

    private static final int NUMBER_OF_SITES = 20;

    private static final String OUTPUT_DIRECTORY = "output/";
    private static final String FILE_PREFIX = "transaction-";
    private Map<String, String> siteMap = new HashMap<String, String>();
    private Map<String, Patient> patientMap = new HashMap<String, Patient>();
    private PatientObfuscator obfuscator = new PatientObfuscator();
    private List<ClinicalData> dataList;
    private static final boolean SHOW_GUI = false;


    public void buildPatientSet(List<ClinicalData> dataList) {
        this.dataList = dataList;
        for (ClinicalData data : dataList) {


            for (SubjectData subjectData : data.getSubjectData()) {
                cleanAnnotations(subjectData.getAnnotation());
                obfuscateAuditRecord(subjectData.getAuditRecord());
                Patient p = patientMap.get(subjectData.getSubjectKey());

                //Look here to figure out how to scramble stuff
                if (p == null) {
                    p = new Patient();
                    p.setSubjectKey(subjectData.getSubjectKey());
                    patientMap.put(subjectData.getSubjectKey(), p);
                }

                subjectData.getSiteRef().setLocationOID(deidentifySite(subjectData.getSiteRef().getLocationOID()));
                for (StudyEventData studyEventData : subjectData.getStudyEventData()) {
                    cleanAnnotations(studyEventData.getAnnotation());
                    obfuscateAuditRecord(studyEventData.getAuditRecord());

                    for (FormData formData : studyEventData.getFormData()) {
                        cleanAnnotations(formData.getAnnotation());
                        obfuscateAuditRecord(formData.getAuditRecord());
                        for (ItemGroupData itemGroupData : formData.getItemGroupData()) {
                            cleanAnnotations(itemGroupData.getAnnotation());
                            obfuscateAuditRecord(itemGroupData.getAuditRecord());
                            for (ItemData itemData : itemGroupData.getItemData()) {
                                cleanAnnotations(itemData.getAnnotation());
                                LocationRef ref = itemData.getAuditRecord().getLocationRef();
                                ref.setLocationOID(deidentifySite(ref.getLocationOID()));
                                p.addItem(itemData);

                            }
                        }
                    }
                }
            }
        }


    }

    public void transformPatientSet() {


        for (Patient p : patientMap.values()) {
            obfuscator.obfuscate(p);
        }

        //TODO big hack to push back the obfuscated subject Keys
        for (ClinicalData data : dataList) {
            for (SubjectData subjectData : data.getSubjectData()) {
                Patient p = patientMap.get(subjectData.getSubjectKey());
                if (p != null) {
                    subjectData.setSubjectKey(p.getSubjectKey());
                }
            }
        }


    }


    protected void createSiteMapping(InformClientAPI client)
            throws IOException, JAXBException,
            XPathException, ParserConfigurationException,
            SAXException {

        String stringResult = client.getSiteList();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(stringResult));
        Document d = builder.parse(is);

        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList result1 = (NodeList) xPath.evaluate("GetSiteListResult/CRFSUBMIT/SITELIST", stringResult,
                XPathConstants.NODESET);


    }


    private String deidentifySite(String site) {
        if (!siteMap.containsKey(site)) {
            siteMap.put(site, generateRandomNumberString());
        }
        return siteMap.get(site);
    }


    private static String generateRandomNumberString() {
        Random rand = new SecureRandom();
        return Long.toString(Math.abs(rand.nextLong()) % NUMBER_OF_SITES);
    }


    public static void main(String[] args) throws Exception {


        InformClient informClient =
                new InformClient("carranet", "", "",
                        "https://informadapterqa.tch.harvard.edu/InFormAdapter/ODM/InFormODM.asmx");

        InformClient.disableSSLTrust();


        List<ClinicalData> clinicalDataList = new ArrayList<ClinicalData>();

        List<ClinicalData> clincalDataListOld = new ArrayList<ClinicalData>();
        List<ResponseODM> odmList = getInformData(informClient);


        for (ResponseODM response : odmList) {
            clinicalDataList.addAll(response.getODM().getClinicalData());

        }

        if (SHOW_GUI) {
            for (ResponseODM response : getInformData(informClient)) {
                clincalDataListOld.addAll(response.getODM().getClinicalData());
            }
        }


        //Make new 'output directory'
        File newDirectory = new File(OUTPUT_DIRECTORY);
        if (!newDirectory.exists()) {
            newDirectory.mkdir();
        }


        ODMDeidentifyProcessor processor = new ODMDeidentifyProcessor();
        processor.buildPatientSet(clinicalDataList);
        processor.transformPatientSet();


        if (SHOW_GUI) {
            ODMDeidentifyProcessor processorOldData = new ODMDeidentifyProcessor();
//            processorOldData.buildPatientSet(clincalDataListOld);
//
//            PatientChartDiffer chart = new PatientChartDiffer("Differences",
//                    processor.getPatientMap(), processorOldData.getPatientMap());
//            chart.pack();
//            RefineryUtilities.centerFrameOnScreen(chart);
//            chart.setVisible(true);


        } else {
            processor.transformPatientSet();
        }


        Marshaller m = InformJAXBContext.getInstance().getContext().createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        int fileCount = 1;


        for (ResponseODM data : odmList) {
            GetTransactionsResult tr = new GetTransactionsResult();
            ODM odm = new ODM();
            odm.getClinicalData().addAll(data.getODM().getClinicalData());
            tr.setAny(data);


            File f = new File(OUTPUT_DIRECTORY + FILE_PREFIX + fileCount + ".xml");
            FileWriter fw = new FileWriter(f);

            fileCount++;
            m.marshal(tr, fw);
        }

    }


    private static final List<ResponseODM> getInformData(InformClientAPI api) throws IOException {
        String oldBookmark = "oldBookMark";
        String newBookmark = "";
        List<ResponseODM> odmList = new ArrayList<ResponseODM>();

        while (!newBookmark.equals(oldBookmark)) {
            System.out.println("NEW BOOKMARK " + newBookmark);
            ResponseODM response = api.getTransaction(newBookmark);
            oldBookmark = newBookmark;
            newBookmark = response.getBookmark();
            if (response.getODM() != null && response.getODM().getClinicalData().size() > 0) {

                odmList.add(response);
            }
        }
        return odmList;

    }


    public Map<String, Patient> getPatientMap() {
        return patientMap;
    }

    public void setPatientMap(Map<String, Patient> patientMap) {
        this.patientMap = patientMap;
    }

    private void cleanAnnotations(List<Annotation> annotations) {
        for (Annotation e : annotations) {
            Comment newComment = new Comment();
            newComment.setValue("");
            e.setComment(newComment);
        }
    }

    private void obfuscateAuditRecord(AuditRecord record) {
        if (record != null && record.getReasonForChange() != null) {
            ReasonForChange f = record.getReasonForChange();
            if (!f.getValue().matches(ReasonForChangeObfuscator.matcher)) {
                f.setValue("Other Reason");
            }

        }


        if (record != null && record.getDateTimeStamp() != null) {
            record.setLocationRef(new LocationRef());
            record.getUserRef().setUserOID("obfusc");
            record.getDateTimeStamp().setValue(new DateTime("2010-07-01T00:00:00-05:00").toString());
        }
    }


}

