
package com.phaseforward.informadapter.odm._2;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TotalTransactionCount" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="TransactionXmlCount" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="TransactionXmlRemaining" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="LastTransactionXmlTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "totalTransactionCount",
    "transactionXmlCount",
    "transactionXmlRemaining",
    "lastTransactionXmlTime"
})
@XmlRootElement(name = "TransactionStatus")
public class TransactionStatus {

    @XmlElement(name = "TotalTransactionCount")
    protected BigInteger totalTransactionCount;
    @XmlElement(name = "TransactionXmlCount")
    protected BigInteger transactionXmlCount;
    @XmlElement(name = "TransactionXmlRemaining")
    protected BigInteger transactionXmlRemaining;
    @XmlElement(name = "LastTransactionXmlTime")
    protected String lastTransactionXmlTime;

    /**
     * Gets the value of the totalTransactionCount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalTransactionCount() {
        return totalTransactionCount;
    }

    /**
     * Sets the value of the totalTransactionCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalTransactionCount(BigInteger value) {
        this.totalTransactionCount = value;
    }

    /**
     * Gets the value of the transactionXmlCount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTransactionXmlCount() {
        return transactionXmlCount;
    }

    /**
     * Sets the value of the transactionXmlCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTransactionXmlCount(BigInteger value) {
        this.transactionXmlCount = value;
    }

    /**
     * Gets the value of the transactionXmlRemaining property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTransactionXmlRemaining() {
        return transactionXmlRemaining;
    }

    /**
     * Sets the value of the transactionXmlRemaining property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTransactionXmlRemaining(BigInteger value) {
        this.transactionXmlRemaining = value;
    }

    /**
     * Gets the value of the lastTransactionXmlTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastTransactionXmlTime() {
        return lastTransactionXmlTime;
    }

    /**
     * Sets the value of the lastTransactionXmlTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastTransactionXmlTime(String value) {
        this.lastTransactionXmlTime = value;
    }

}
