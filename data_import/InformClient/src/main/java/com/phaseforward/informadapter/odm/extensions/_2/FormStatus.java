
package com.phaseforward.informadapter.odm.extensions._2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.cdisc.ns.odm.v1.AuditRecord;
import org.cdisc.ns.odm.v1.YesOrNo;


/**
 * <p>Java class for FormStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FormStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}AuditRecord"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Activated" type="{http://www.cdisc.org/ns/odm/v1.3}YesOrNo" />
 *       &lt;attribute name="Frozen" type="{http://www.cdisc.org/ns/odm/v1.3}YesOrNo" />
 *       &lt;attribute name="Locked" type="{http://www.cdisc.org/ns/odm/v1.3}YesOrNo" />
 *       &lt;attribute name="Deleted" type="{http://www.cdisc.org/ns/odm/v1.3}YesOrNo" />
 *       &lt;attribute name="SVReady" type="{http://www.cdisc.org/ns/odm/v1.3}YesOrNo" />
 *       &lt;attribute name="SVPartial" type="{http://www.cdisc.org/ns/odm/v1.3}YesOrNo" />
 *       &lt;attribute name="SVComplete" type="{http://www.cdisc.org/ns/odm/v1.3}YesOrNo" />
 *       &lt;attribute name="Signed" type="{http://www.cdisc.org/ns/odm/v1.3}YesOrNo" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FormStatus", propOrder = {
    "auditRecord"
})
public class FormStatus {

    @XmlElement(name = "AuditRecord", namespace = "http://www.cdisc.org/ns/odm/v1.3", required = true)
    protected AuditRecord auditRecord;
    @XmlAttribute(name = "Activated")
    protected YesOrNo activated;
    @XmlAttribute(name = "Frozen")
    protected YesOrNo frozen;
    @XmlAttribute(name = "Locked")
    protected YesOrNo locked;
    @XmlAttribute(name = "Deleted")
    protected YesOrNo deleted;
    @XmlAttribute(name = "SVReady")
    protected YesOrNo svReady;
    @XmlAttribute(name = "SVPartial")
    protected YesOrNo svPartial;
    @XmlAttribute(name = "SVComplete")
    protected YesOrNo svComplete;
    @XmlAttribute(name = "Signed")
    protected YesOrNo signed;

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
     * Gets the value of the activated property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getActivated() {
        return activated;
    }

    /**
     * Sets the value of the activated property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setActivated(YesOrNo value) {
        this.activated = value;
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
     * Gets the value of the deleted property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getDeleted() {
        return deleted;
    }

    /**
     * Sets the value of the deleted property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setDeleted(YesOrNo value) {
        this.deleted = value;
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
     * Gets the value of the svPartial property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getSVPartial() {
        return svPartial;
    }

    /**
     * Sets the value of the svPartial property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setSVPartial(YesOrNo value) {
        this.svPartial = value;
    }

    /**
     * Gets the value of the svComplete property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getSVComplete() {
        return svComplete;
    }

    /**
     * Sets the value of the svComplete property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setSVComplete(YesOrNo value) {
        this.svComplete = value;
    }

    /**
     * Gets the value of the signed property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getSigned() {
        return signed;
    }

    /**
     * Sets the value of the signed property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setSigned(YesOrNo value) {
        this.signed = value;
    }

}
