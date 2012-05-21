
package com.phaseforward.informadapter.odm._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DownloadMetadataODM complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DownloadMetadataODM">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="trial" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="userName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DownloadMetadataODM")
@XmlSeeAlso({
    com.phaseforward.informadapter.odm._2.DownloadMetadataODM.class
})
public class DownloadMetadataODM {

    @XmlAttribute(required = true)
    protected String trial;
    @XmlAttribute
    protected String userName;

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

}
