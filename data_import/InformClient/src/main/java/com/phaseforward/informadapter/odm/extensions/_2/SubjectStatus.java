
package com.phaseforward.informadapter.odm.extensions._2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.cdisc.ns.odm.v1.AuditRecord;
import org.cdisc.ns.odm.v1.YesOrNo;


/**
 * <p>Java class for SubjectStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SubjectStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}AuditRecord" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Frozen" type="{http://www.cdisc.org/ns/odm/v1.3}YesOrNo" />
 *       &lt;attribute name="Locked" type="{http://www.cdisc.org/ns/odm/v1.3}YesOrNo" />
 *       &lt;attribute name="SVReady" type="{http://www.cdisc.org/ns/odm/v1.3}YesOrNo" />
 *       &lt;attribute name="State" type="{http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0}SubjectState" />
 *       &lt;attribute name="ScreenFailReason" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="EnrollFailReason" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="EnrollOverrideReason" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DropOutReason" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubjectStatus", propOrder = {
    "auditRecord"
})
public class SubjectStatus {

    @XmlElement(name = "AuditRecord", namespace = "http://www.cdisc.org/ns/odm/v1.3")
    protected AuditRecord auditRecord;
    @XmlAttribute(name = "Frozen")
    protected YesOrNo frozen;
    @XmlAttribute(name = "Locked")
    protected YesOrNo locked;
    @XmlAttribute(name = "SVReady")
    protected YesOrNo svReady;
    @XmlAttribute(name = "State")
    protected SubjectState state;
    @XmlAttribute(name = "ScreenFailReason")
    protected String screenFailReason;
    @XmlAttribute(name = "EnrollFailReason")
    protected String enrollFailReason;
    @XmlAttribute(name = "EnrollOverrideReason")
    protected String enrollOverrideReason;
    @XmlAttribute(name = "DropOutReason")
    protected String dropOutReason;

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
     * Gets the value of the frozen property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getFrozen() {
        return frozen;
    }

    /**
     * Sets the value of the frozen property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setFrozen(YesOrNo value) {
        this.frozen = value;
    }

    /**
     * Gets the value of the locked property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getLocked() {
        return locked;
    }

    /**
     * Sets the value of the locked property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setLocked(YesOrNo value) {
        this.locked = value;
    }

    /**
     * Gets the value of the svReady property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getSVReady() {
        return svReady;
    }

    /**
     * Sets the value of the svReady property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setSVReady(YesOrNo value) {
        this.svReady = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link SubjectState }
     *     
     */
    public SubjectState getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubjectState }
     *     
     */
    public void setState(SubjectState value) {
        this.state = value;
    }

    /**
     * Gets the value of the screenFailReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreenFailReason() {
        return screenFailReason;
    }

    /**
     * Sets the value of the screenFailReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreenFailReason(String value) {
        this.screenFailReason = value;
    }

    /**
     * Gets the value of the enrollFailReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnrollFailReason() {
        return enrollFailReason;
    }

    /**
     * Sets the value of the enrollFailReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnrollFailReason(String value) {
        this.enrollFailReason = value;
    }

    /**
     * Gets the value of the enrollOverrideReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnrollOverrideReason() {
        return enrollOverrideReason;
    }

    /**
     * Sets the value of the enrollOverrideReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnrollOverrideReason(String value) {
        this.enrollOverrideReason = value;
    }

    /**
     * Gets the value of the dropOutReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDropOutReason() {
        return dropOutReason;
    }

    /**
     * Sets the value of the dropOutReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDropOutReason(String value) {
        this.dropOutReason = value;
    }

}
