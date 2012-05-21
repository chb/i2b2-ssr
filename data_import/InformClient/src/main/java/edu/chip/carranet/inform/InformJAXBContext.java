package edu.chip.carranet.inform;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * The cached context for all inform/odm JAXB objects
 *
 * @author Justin Quan
 * @version %I% Date: 2/24/11
 */
public class InformJAXBContext {
    private static InformJAXBContext instance;

    private String packageList;
    private JAXBContext context;

    private InformJAXBContext() throws JAXBException {

        String[] packageNames = {
                "com.phaseforward.adapterframework.export._1",
                "com.phaseforward.informadapter.odm._1",
                "com.phaseforward.informadapter.odm._2",
                "com.phaseforward.informadapter.odm.extensions._2",
                "org.cdisc.ns.odm.v1",
                "org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0",
                "org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0",
                "org.w3._2000._09.xmldsig_" };
        final StringBuilder sb = new StringBuilder();

        for(final String s : packageNames)
        {
            sb.append(s).append(":");
        }

        this.packageList = sb.toString();
        this.context = JAXBContext.newInstance(packageList);

    }

    public static synchronized InformJAXBContext getInstance() throws JAXBException {
        if(instance == null) {
            instance = new InformJAXBContext();
        }

        return instance;
    }

    public String getPackageList() {
        return packageList;
    }

    public JAXBContext getContext() {
        return context;
    }

}
