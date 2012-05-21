
package com.phaseforward.informadapter.odm.extensions._2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.cdisc.ns.odm.v1.AuditRecord;
import org.cdisc.ns.odm.v1.YesOrNo;


/**
 * <p>Java class for StudyEventStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StudyEventStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}AuditRecord"/>
 *       &lt;/sequence>
 *       &lt;attribute name="StudyEventOID" type="{http://www.cdisc.org/ns/odm/v1.3}oid" />
 *       &lt;attribute name="DynamicRepeatingActivated" type="{http://www.cdisc.org/ns/odm/v1.3}YesOrNo" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudyEventStatus", propOrder = {
    "auditRecord"
})
public class StudyEventStatus {

    @XmlElement(name = "AuditRecord", namespace = "http://www.cdisc.org/ns/odm/v1.3", required = true)
    protected AuditRecord auditRecord;
    @XmlAttribute(name = "StudyEventOID")
    protected String studyEventOID;
    @XmlAttribute(name = "DynamicRepeatingActivated")
    protected YesOrNo dynamicRepeatingActivated;

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
     * Gets the value of the studyEventOID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudyEventOID() {
        return studyEventOID;
    }

    /**
     * Sets the value of the studyEventOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudyEventOID(String value) {
        this.studyEventOID = value;
    }

    /**
     * Gets the value of the dynamicRepeatingActivated property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getDynamicRepeatingActivated() {
        return dynamicRepeatingActivated;
    }

    /**
     * Sets the value of the dynamicRepeatingActivated property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setDynamicRepeatingActivated(YesOrNo value) {
        this.dynamicRepeatingActivated = value;
    }

}
