
package com.phaseforward.informadapter.odm._1;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DownloadPatientDataODM complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DownloadPatientDataODM">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="trial" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="userName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="patientID" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="visitID" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="visitIndex" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *       &lt;attribute name="formID" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="formRevision" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *       &lt;attribute name="skipODMReduction" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DownloadPatientDataODM")
@XmlSeeAlso({
    com.phaseforward.informadapter.odm._2.DownloadPatientDataODM.class
})
public class DownloadPatientDataODM {

    @XmlAttribute(required = true)
    protected String trial;
    @XmlAttribute
    protected String userName;
    @XmlAttribute(required = true)
    protected long patientID;
    @XmlAttribute(required = true)
    protected long visitID;
    @XmlAttribute(required = true)
    protected BigDecimal visitIndex;
    @XmlAttribute(required = true)
    protected long formID;
    @XmlAttribute(required = true)
    protected BigDecimal formRevision;
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
     * Gets the value of the patientID property.
     * 
     */
    public long getPatientID() {
        return patientID;
    }

    /**
     * Sets the value of the patientID property.
     * 
     */
    public void setPatientID(long value) {
        this.patientID = value;
    }

    /**
     * Gets the value of the visitID property.
     * 
     */
    public long getVisitID() {
        return visitID;
    }

    /**
     * Sets the value of the visitID property.
     * 
     */
    public void setVisitID(long value) {
        this.visitID = value;
    }

    /**
     * Gets the value of the visitIndex property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVisitIndex() {
        return visitIndex;
    }

    /**
     * Sets the value of the visitIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVisitIndex(BigDecimal value) {
        this.visitIndex = value;
    }

    /**
     * Gets the value of the formID property.
     * 
     */
    public long getFormID() {
        return formID;
    }

    /**
     * Sets the value of the formID property.
     * 
     */
    public void setFormID(long value) {
        this.formID = value;
    }

    /**
     * Gets the value of the formRevision property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFormRevision() {
        return formRevision;
    }

    /**
     * Sets the value of the formRevision property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFormRevision(BigDecimal value) {
        this.formRevision = value;
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
