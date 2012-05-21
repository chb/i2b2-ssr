
package com.phaseforward.informadapter.odm._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DownloadTransactionODM complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DownloadTransactionODM">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="trial" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="bookmark" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="userName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="skipODMReduction" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DownloadTransactionODM")
@XmlSeeAlso({
    com.phaseforward.informadapter.odm._2.DownloadTransactionODM.class
})
public class DownloadTransactionODM {

    @XmlAttribute(required = true)
    protected String trial;
    @XmlAttribute
    protected String bookmark;
    @XmlAttribute
    protected String userName;
    @XmlAttribute
    protected Boolean skipODMReduction;

    /**
     * Gets the value of the trial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrial() {
        return trial;
    }

    /**
     * Sets the value of the trial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrial(String value) {
        this.trial = value;
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
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the skipODMReduction property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSkipODMReduction() {
        return skipODMReduction;
    }

    /**
     * Sets the value of the skipODMReduction property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSkipODMReduction(Boolean value) {
        this.skipODMReduction = value;
    }

}
