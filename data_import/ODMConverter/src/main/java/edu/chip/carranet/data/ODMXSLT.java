package edu.chip.carranet.data;

import net.sf.saxon.TransformerFactoryImpl;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Justin Quan
 * @link http://chip.org
 * Date: 3/7/11
 */
public class ODMXSLT {
    private static String TRANSFORMER_CLASS = "net.sf.saxon.TransformerFactoryImpl";

    private static String STEP_ONE_FILE = "/Step1.xslt";
    private static String STEP_TWO_FILE = "/Step2.xslt";

    private static TransformerFactory transformerFactory;
    private static Transformer stepOneTransform;
    private static Transformer stepTwoTransform;

    public ODMXSLT() throws TransformerConfigurationException {
        // pass this path to the StreamSource which needs it to resolve the carranetInclude.xslt dependency
        String path = getClass().getResource(STEP_ONE_FILE).toExternalForm();
        String systemId = path.substring(0,path.lastIndexOf('/')+1);

        Source xsltStepOneSource = new StreamSource(
                new InputStreamReader(getClass().getResourceAsStream(STEP_ONE_FILE)), systemId);

        Source xsltStepTwoSource = new StreamSource(
                new InputStreamReader(getClass().getResourceAsStream(STEP_TWO_FILE)), systemId);

        transformerFactory = TransformerFactoryImpl.newInstance(TRANSFORMER_CLASS, getClass().getClassLoader());
        stepOneTransform = transformerFactory.newTransformer(xsltStepOneSource);
        stepTwoTransform = transformerFactory.newTransformer(xsltStepTwoSource);
    }

    public String transformToReportSql(InputStream xml) throws TransformerException {
        return doTransform(stepOneTransform, xml);
    }

    public String transformToProcessSql(InputStream xml) throws TransformerException {
        return doTransform(stepTwoTransform, xml);
    }

    private String doTransform(Transformer t, InputStream xml) throws TransformerException {
        Source source = new StreamSource(xml);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StreamResult sqlResult = new StreamResult(baos);
        t.transform(source, sqlResult);
        return new String(baos.toByteArray()).replace("\n", " ").replace("\t", " ");
    }

}
