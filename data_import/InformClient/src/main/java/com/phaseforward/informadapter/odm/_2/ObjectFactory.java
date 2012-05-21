
package com.phaseforward.informadapter.odm._2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.phaseforward.informadapter.odm._2 package. 
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

    private final static QName _ResponseODM_QNAME = new QName("http://www.phaseforward.com/InFormAdapter/ODM/2.0", "ResponseODM");
    private final static QName _DownloadMetadataODM_QNAME = new QName("http://www.phaseforward.com/InFormAdapter/ODM/2.0", "DownloadMetadataODM");
    private final static QName _DownloadAdminDataODM_QNAME = new QName("http://www.phaseforward.com/InFormAdapter/ODM/2.0", "DownloadAdminDataODM");
    private final static QName _DownloadTransactionODM_QNAME = new QName("http://www.phaseforward.com/InFormAdapter/ODM/2.0", "DownloadTransactionODM");
    private final static QName _DownloadPatientDataODM_QNAME = new QName("http://www.phaseforward.com/InFormAdapter/ODM/2.0", "DownloadPatientDataODM");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.phaseforward.informadapter.odm._2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ResponseODM }
     * 
     */
    public ResponseODM createResponseODM() {
        return new ResponseODM();
    }

    /**
     * Create an instance of {@link TransactionStatus }
     * 
     */
    public TransactionStatus createTransactionStatus() {
        return new TransactionStatus();
    }

    /**
     * Create an instance of {@link DownloadMetadataODM }
     * 
     */
    public DownloadMetadataODM createDownloadMetadataODM() {
        return new DownloadMetadataODM();
    }

    /**
     * Create an instance of {@link DownloadPatientDataODM }
     * 
     */
    public DownloadPatientDataODM createDownloadPatientDataODM() {
        return new DownloadPatientDataODM();
    }

    /**
     * Create an instance of {@link DownloadAdminDataODM }
     * 
     */
    public DownloadAdminDataODM createDownloadAdminDataODM() {
        return new DownloadAdminDataODM();
    }

    /**
     * Create an instance of {@link DownloadTransactionODM }
     * 
     */
    public DownloadTransactionODM createDownloadTransactionODM() {
        return new DownloadTransactionODM();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseODM }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.phaseforward.com/InFormAdapter/ODM/2.0", name = "ResponseODM")
    public JAXBElement<ResponseODM> createResponseODM(ResponseODM value) {
        return new JAXBElement<ResponseODM>(_ResponseODM_QNAME, ResponseODM.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadMetadataODM }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.phaseforward.com/InFormAdapter/ODM/2.0", name = "DownloadMetadataODM")
    public JAXBElement<DownloadMetadataODM> createDownloadMetadataODM(DownloadMetadataODM value) {
        return new JAXBElement<DownloadMetadataODM>(_DownloadMetadataODM_QNAME, DownloadMetadataODM.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadAdminDataODM }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.phaseforward.com/InFormAdapter/ODM/2.0", name = "DownloadAdminDataODM")
    public JAXBElement<DownloadAdminDataODM> createDownloadAdminDataODM(DownloadAdminDataODM value) {
        return new JAXBElement<DownloadAdminDataODM>(_DownloadAdminDataODM_QNAME, DownloadAdminDataODM.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadTransactionODM }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.phaseforward.com/InFormAdapter/ODM/2.0", name = "DownloadTransactionODM")
    public JAXBElement<DownloadTransactionODM> createDownloadTransactionODM(DownloadTransactionODM value) {
        return new JAXBElement<DownloadTransactionODM>(_DownloadTransactionODM_QNAME, DownloadTransactionODM.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadPatientDataODM }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.phaseforward.com/InFormAdapter/ODM/2.0", name = "DownloadPatientDataODM")
    public JAXBElement<DownloadPatientDataODM> createDownloadPatientDataODM(DownloadPatientDataODM value) {
        return new JAXBElement<DownloadPatientDataODM>(_DownloadPatientDataODM_QNAME, DownloadPatientDataODM.class, null, value);
    }

}
