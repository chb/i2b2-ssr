
package com.phaseforward.informadapter.odm._1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.phaseforward.informadapter.odm._1 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DownloadMetadataODM_QNAME = new QName("http://www.phaseforward.com/InFormAdapter/ODM/1.0", "DownloadMetadataODM");
    private final static QName _DownloadAdminDataODM_QNAME = new QName("http://www.phaseforward.com/InFormAdapter/ODM/1.0", "DownloadAdminDataODM");
    private final static QName _LocalizationHeader_QNAME = new QName("http://www.phaseforward.com/InFormAdapter/ODM/1.0", "LocalizationHeader");
    private final static QName _DownloadTransactionODM_QNAME = new QName("http://www.phaseforward.com/InFormAdapter/ODM/1.0", "DownloadTransactionODM");
    private final static QName _WebServiceResponseVersionHeader_QNAME = new QName("http://www.phaseforward.com/InFormAdapter/ODM/1.0", "WebServiceResponseVersionHeader");
    private final static QName _DownloadPatientDataODM_QNAME = new QName("http://www.phaseforward.com/InFormAdapter/ODM/1.0", "DownloadPatientDataODM");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.phaseforward.informadapter.odm._1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LocalizationHeader }
     * 
     */
    public LocalizationHeader createLocalizationHeader() {
        return new LocalizationHeader();
    }

    /**
     * Create an instance of {@link GetPatientFormsResult }
     * 
     */
    public GetPatientFormsResult createGetPatientFormsResult() {
        return new GetPatientFormsResult();
    }

    /**
     * Create an instance of {@link GetTransactionsResult }
     * 
     */
    public GetTransactionsResult createGetTransactionsResult() {
        return new GetTransactionsResult();
    }

    /**
     * Create an instance of {@link Node }
     * 
     */
    public Node createNode() {
        return new Node();
    }

    /**
     * Create an instance of {@link GetPatientList }
     * 
     */
    public GetPatientList createGetPatientList() {
        return new GetPatientList();
    }

    /**
     * Create an instance of {@link DownloadMetadataODM }
     * 
     */
    public DownloadMetadataODM createDownloadMetadataODM() {
        return new DownloadMetadataODM();
    }

    /**
     * Create an instance of {@link DownloadTransactionODM }
     * 
     */
    public DownloadTransactionODM createDownloadTransactionODM() {
        return new DownloadTransactionODM();
    }

    /**
     * Create an instance of {@link DownloadAdminDataODMResult }
     * 
     */
    public DownloadAdminDataODMResult createDownloadAdminDataODMResult() {
        return new DownloadAdminDataODMResult();
    }

    /**
     * Create an instance of {@link GetTransactionStatusResult }
     * 
     */
    public GetTransactionStatusResult createGetTransactionStatusResult() {
        return new GetTransactionStatusResult();
    }

    /**
     * Create an instance of {@link GetPatientForms }
     * 
     */
    public GetPatientForms createGetPatientForms() {
        return new GetPatientForms();
    }

    /**
     * Create an instance of {@link WebServiceResponseVersionHeader }
     * 
     */
    public WebServiceResponseVersionHeader createWebServiceResponseVersionHeader() {
        return new WebServiceResponseVersionHeader();
    }

    /**
     * Create an instance of {@link GetSiteListResult }
     * 
     */
    public GetSiteListResult createGetSiteListResult() {
        return new GetSiteListResult();
    }

    /**
     * Create an instance of {@link DownloadAdminDataODM }
     * 
     */
    public DownloadAdminDataODM createDownloadAdminDataODM() {
        return new DownloadAdminDataODM();
    }

    /**
     * Create an instance of {@link GetPatientListResult }
     * 
     */
    public GetPatientListResult createGetPatientListResult() {
        return new GetPatientListResult();
    }

    /**
     * Create an instance of {@link DownloadPatientDataODM }
     * 
     */
    public DownloadPatientDataODM createDownloadPatientDataODM() {
        return new DownloadPatientDataODM();
    }

    /**
     * Create an instance of {@link DownloadMetadataODMResult }
     * 
     */
    public DownloadMetadataODMResult createDownloadMetadataODMResult() {
        return new DownloadMetadataODMResult();
    }

    /**
     * Create an instance of {@link GetSiteList }
     * 
     */
    public GetSiteList createGetSiteList() {
        return new GetSiteList();
    }

    /**
     * Create an instance of {@link DownloadPatientDataODMResult }
     * 
     */
    public DownloadPatientDataODMResult createDownloadPatientDataODMResult() {
        return new DownloadPatientDataODMResult();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadMetadataODM }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.phaseforward.com/InFormAdapter/ODM/1.0", name = "DownloadMetadataODM")
    public JAXBElement<DownloadMetadataODM> createDownloadMetadataODM(DownloadMetadataODM value) {
        return new JAXBElement<DownloadMetadataODM>(_DownloadMetadataODM_QNAME, DownloadMetadataODM.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadAdminDataODM }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.phaseforward.com/InFormAdapter/ODM/1.0", name = "DownloadAdminDataODM")
    public JAXBElement<DownloadAdminDataODM> createDownloadAdminDataODM(DownloadAdminDataODM value) {
        return new JAXBElement<DownloadAdminDataODM>(_DownloadAdminDataODM_QNAME, DownloadAdminDataODM.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LocalizationHeader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.phaseforward.com/InFormAdapter/ODM/1.0", name = "LocalizationHeader")
    public JAXBElement<LocalizationHeader> createLocalizationHeader(LocalizationHeader value) {
        return new JAXBElement<LocalizationHeader>(_LocalizationHeader_QNAME, LocalizationHeader.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadTransactionODM }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.phaseforward.com/InFormAdapter/ODM/1.0", name = "DownloadTransactionODM")
    public JAXBElement<DownloadTransactionODM> createDownloadTransactionODM(DownloadTransactionODM value) {
        return new JAXBElement<DownloadTransactionODM>(_DownloadTransactionODM_QNAME, DownloadTransactionODM.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebServiceResponseVersionHeader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.phaseforward.com/InFormAdapter/ODM/1.0", name = "WebServiceResponseVersionHeader")
    public JAXBElement<WebServiceResponseVersionHeader> createWebServiceResponseVersionHeader(WebServiceResponseVersionHeader value) {
        return new JAXBElement<WebServiceResponseVersionHeader>(_WebServiceResponseVersionHeader_QNAME, WebServiceResponseVersionHeader.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadPatientDataODM }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.phaseforward.com/InFormAdapter/ODM/1.0", name = "DownloadPatientDataODM")
    public JAXBElement<DownloadPatientDataODM> createDownloadPatientDataODM(DownloadPatientDataODM value) {
        return new JAXBElement<DownloadPatientDataODM>(_DownloadPatientDataODM_QNAME, DownloadPatientDataODM.class, null, value);
    }

}
