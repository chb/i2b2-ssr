
package com.phaseforward.informadapter.odm.extensions._2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.cdisc.ns.odm.v1.AuditRecord;


/**
 * <p>Java class for Candidate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Candidate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}AuditRecord"/>
 *       &lt;/sequence>
 *       &lt;attribute name="SubjectInitials" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="EnrollmentNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ScreeningNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ScreeningFailure" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="EnrollmentFailure" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="EnrollmentOverride" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DateOfBirth" type="{http://www.cdisc.org/ns/odm/v1.3}text" />
 *       &lt;attribute name="ScreeningDate" type="{http://www.cdisc.org/ns/odm/v1.3}text" />
 *       &lt;attribute name="PatientNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="PatientRevision" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Candidate", propOrder = {
    "auditRecord"
})
public class Candidate {

    @XmlElement(name = "AuditRecord", namespace = "http://www.cdisc.org/ns/odm/v1.3", required = true)
    protected AuditRecord auditRecord;
    @XmlAttribute(name = "SubjectInitials")
    protected String subjectInitials;
    @XmlAttribute(name = "EnrollmentNumber")
    protected String enrollmentNumber;
    @XmlAttribute(name = "ScreeningNumber")
    protected String screeningNumber;
    @XmlAttribute(name = "ScreeningFailure")
    protected String screeningFailure;
    @XmlAttribute(name = "EnrollmentFailure")
    protected String enrollmentFailure;
    @XmlAttribute(name = "EnrollmentOverride")
    protected String enrollmentOverride;
    @XmlAttribute(name = "DateOfBirth")
    protected String dateOfBirth;
    @XmlAttribute(name = "ScreeningDate")
    protected String screeningDate;
    @XmlAttribute(name = "PatientNumber")
    protected String patientNumber;
    @XmlAttribute(name = "PatientRevision")
    protected String patientRevision;

    /**
     * Gets the value of the auditRecord property.
     * 
     * @return
     *     possible object is
     *     {@link AuditRecord }
     *     
     */
    public AuditRecord getAuditRecord() {
        return auditRecord;
    }

    /**
     * Sets the value of the auditRecord property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuditRecord }
     *     
     */
    public void setAuditRecord(AuditRecord value) {
        this.auditRecord = value;
    }

    /**
     * Gets the value of the subjectInitials property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubjectInitials() {
        return subjectInitials;
    }

    /**
     * Sets the value of the subjectInitials property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubjectInitials(String value) {
        this.subjectInitials = value;
    }

    /**
     * Gets the value of the enrollmentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnrollmentNumber() {
        return enrollmentNumber;
    }

    /**
     * Sets the value of the enrollmentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnrollmentNumber(String value) {
        this.enrollmentNumber = value;
    }

    /**
     * Gets the value of the screeningNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreeningNumber() {
        return screeningNumber;
    }

    /**
     * Sets the value of the screeningNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreeningNumber(String value) {
        this.screeningNumber = value;
    }

    /**
     * Gets the value of the screeningFailure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreeningFailure() {
        return screeningFailure;
    }

    /**
     * Sets the value of the screeningFailure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreeningFailure(String value) {
        this.screeningFailure = value;
    }

    /**
     * Gets the value of the enrollmentFailure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnrollmentFailure() {
        return enrollmentFailure;
    }

    /**
     * Sets the value of the enrollmentFailure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnrollmentFailure(String value) {
        this.enrollmentFailure = value;
    }

    /**
     * Gets the value of the enrollmentOverride property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnrollmentOverride() {
        return enrollmentOverride;
    }

    /**
     * Sets the value of the enrollmentOverride property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnrollmentOverride(String value) {
        this.enrollmentOverride = value;
    }

    /**
     * Gets the value of the dateOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the value of the dateOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateOfBirth(String value) {
        this.dateOfBirth = value;
    }

    /**
     * Gets the value of the screeningDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreeningDate() {
        return screeningDate;
    }

    /**
     * Sets the value of the screeningDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreeningDate(String value) {
        this.screeningDate = value;
    }

    /**
     * Gets the value of the patientNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientNumber() {
        return patientNumber;
    }

    /**
     * Sets the value of the patientNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientNumber(String value) {
        this.patientNumber = value;
    }

    /**
     * Gets the value of the patientRevision property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientRevision() {
        return patientRevision;
    }

    /**
     * Sets the value of the patientRevision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientRevision(String value) {
        this.patientRevision = value;
    }

}
