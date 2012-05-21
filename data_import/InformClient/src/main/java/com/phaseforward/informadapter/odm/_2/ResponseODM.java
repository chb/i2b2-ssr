
package com.phaseforward.informadapter.odm._2;

import org.cdisc.ns.odm.v1.ODM;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;


/**
 * <p>Java class for ResponseODM complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResponseODM">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}ODM" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element name="Status" type="{http://www.phaseforward.com/InFormAdapter/ODM/2.0}OutputStatus" minOccurs="0"/>
 *         &lt;element name="Bookmark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TransactionsProcessed" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "ResponseODM")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseODM", propOrder = {
    "odm",
    "status",
    "bookmark",
    "transactionsProcessed"
})
public class ResponseODM {

    @XmlElement(name = "ODM", namespace = "http://www.cdisc.org/ns/odm/v1.3")
    protected ODM odm;
    @XmlElement(name = "Status", defaultValue = "OK")
    protected OutputStatus status;
    @XmlElement(name = "Bookmark")
    protected String bookmark;
    @XmlElement(name = "TransactionsProcessed")
    protected BigInteger transactionsProcessed;

    /**
     * Gets the value of the odm property.
     * 
     * @return
     *     possible object is
     *     {@link ODM }
     *     
     */
    public ODM getODM() {
        return odm;
    }

    /**
     * Sets the value of the odm property.
     * 
     * @param value
     *     allowed object is
     *     {@link ODM }
     *     
     */
    public void setODM(ODM value) {
        this.odm = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link OutputStatus }
     *     
     */
    public OutputStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link OutputStatus }
     *     
     */
    public void setStatus(OutputStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the bookmark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookmark() {
        return bookmark;
    }

    /**
     * Sets the value of the bookmark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookmark(String value) {
        this.bookmark = value;
    }

    /**
     * Gets the value of the transactionsProcessed property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTransactionsProcessed() {
        return transactionsProcessed;
    }

    /**
     * Sets the value of the transactionsProcessed property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTransactionsProcessed(BigInteger value) {
        this.transactionsProcessed = value;
    }

}
