
package com.phaseforward.informadapter.odm._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="trial" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="siteID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "GetPatientList")
public class GetPatientList {

    @XmlAttribute(required = true)
    protected String trial;
    @XmlAttribute(required = true)
    protected String siteID;

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
     * Gets the value of the siteID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteID() {
        return siteID;
    }

    /**
     * Sets the value of the siteID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteID(String value) {
        this.siteID = value;
    }

}
