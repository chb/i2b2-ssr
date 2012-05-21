package edu.chip.carranet.carradatapipeline.obfuscation;


import com.phaseforward.informadapter.odm._1.GetTransactionsResult;
import com.phaseforward.informadapter.odm._2.ResponseODM;
import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.ODMDeidentifyProcessor;
import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.Patient;
import edu.chip.carranet.inform.InformJAXBContext;
import org.cdisc.ns.odm.v1.ClinicalData;
import org.junit.Before;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ObfuscationTest {


    List<Patient> patientList = new ArrayList<Patient>();
    private Map<String, Patient> patientMap;
    ODMDeidentifyProcessor processor = new ODMDeidentifyProcessor();
    ODMDeidentifyProcessor unobfuscated = new ODMDeidentifyProcessor();


    @Before
    public void setUp() throws Exception {


        processor.buildPatientSet(generateClinicalData());
        unobfuscated.buildPatientSet(generateClinicalData());


    }

    public void visualizeResults() throws Exception {
        processor.transformPatientSet();

//        PatientChartDiffer testCharts =
//                new PatientChartDiffer("Test", processor.getPatientMap(),
//                        unobfuscated.getPatientMap());
//
//        testCharts.pack();
//        RefineryUtilities.centerFrameOnScreen(testCharts);
//        testCharts.setVisible(true);


    }

    public static void main(String[] args) throws Exception {
        ObfuscationTest test = new ObfuscationTest();
        test.setUp();
        test.visualizeResults();
    }

    private List<ClinicalData> generateClinicalData() throws JAXBException {
        Unmarshaller m = InformJAXBContext.getInstance().getContext().createUnmarshaller();
        URL firstFile = getClass().getClassLoader().getResource("getTrans1.xml");
        URL secondFile = getClass().getClassLoader().getResource("getTrans2.xml");

        GetTransactionsResult tr1 = (GetTransactionsResult) m.unmarshal(new File(firstFile.getFile()));
        GetTransactionsResult tr2 = (GetTransactionsResult) m.unmarshal(new File(secondFile.getFile()));

        ResponseODM odm1 = (ResponseODM) ((JAXBElement) tr1.getAny()).getValue();
        ResponseODM odm2 = (ResponseODM) ((JAXBElement) tr2.getAny()).getValue();

        List<ClinicalData> datas = new ArrayList<ClinicalData>();

        datas.addAll(odm1.getODM().getClinicalData());
        datas.addAll(odm2.getODM().getClinicalData());
        return datas;
    }

}
