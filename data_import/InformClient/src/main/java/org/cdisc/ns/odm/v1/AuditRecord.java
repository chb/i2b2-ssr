
package org.cdisc.ns.odm.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuditRecord complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuditRecord">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}UserRef"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}LocationRef"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}DateTimeStamp"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}ReasonForChange" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}SourceID" minOccurs="0"/>
 *         &lt;group ref="{http://www.cdisc.org/ns/odm/v1.3}AuditRecordElementExtension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}AuditRecordAttributeDefinition"/>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}AuditRecordAttributeExtension"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuditRecord", propOrder = {
    "userRef",
    "locationRef",
    "dateTimeStamp",
    "reasonForChange",
    "sourceID"
})
public class AuditRecord {

    @XmlElement(name = "UserRef", required = true)
    protected UserRef userRef;
    @XmlElement(name = "LocationRef", required = true)
    protected LocationRef locationRef;
    @XmlElement(name = "DateTimeStamp", required = true)
    protected DateTimeStamp dateTimeStamp;
    @XmlElement(name = "ReasonForChange")
    protected ReasonForChange reasonForChange;
    @XmlElement(name = "SourceID")
    protected SourceID sourceID;
    @XmlAttribute(name = "EditPoint")
    protected EditPointType editPoint;
    @XmlAttribute(name = "UsedImputationMethod")
    protected YesOrNo usedImputationMethod;

    /**
     * Gets the value of the userRef property.
     * 
     * @return
     *     possible object is
     *     {@link UserRef }
     *     
     */
    public UserRef getUserRef() {
        return userRef;
    }

    /**
     * Sets the value of the userRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserRef }
     *     
     */
    public void setUserRef(UserRef value) {
        this.userRef = value;
    }

    /**
     * Gets the value of the locationRef property.
     * 
     * @return
     *     possible object is
     *     {@link LocationRef }
     *     
     */
    public LocationRef getLocationRef() {
        return locationRef;
    }

    /**
     * Sets the value of the locationRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationRef }
     *     
     */
    public void setLocationRef(LocationRef value) {
        this.locationRef = value;
    }

    /**
     * Gets the value of the dateTimeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link DateTimeStamp }
     *     
     */
    public DateTimeStamp getDateTimeStamp() {
        return dateTimeStamp;
    }

    /**
     * Sets the value of the dateTimeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTimeStamp }
     *     
     */
    public void setDateTimeStamp(DateTimeStamp value) {
        this.dateTimeStamp = value;
    }

    /**
     * Gets the value of the reasonForChange property.
     * 
     * @return
     *     possible object is
     *     {@link ReasonForChange }
     *     
     */
    public ReasonForChange getReasonForChange() {
        return reasonForChange;
    }

    /**
     * Sets the value of the reasonForChange property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReasonForChange }
     *     
     */
    public void setReasonForChange(ReasonForChange value) {
        this.reasonForChange = value;
    }

    /**
     * Gets the value of the sourceID property.
     * 
     * @return
     *     possible object is
     *     {@link SourceID }
     *     
     */
    public SourceID getSourceID() {
        return sourceID;
    }

    /**
     * Sets the value of the sourceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceID }
     *     
     */
    public void setSourceID(SourceID value) {
        this.sourceID = value;
    }

    /**
     * Gets the value of the editPoint property.
     * 
     * @return
     *     possible object is
     *     {@link EditPointType }
     *     
     */
    public EditPointType getEditPoint() {
        return editPoint;
    }

    /**
     * Sets the value of the editPoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link EditPointType }
     *     
     */
    public void setEditPoint(EditPointType value) {
        this.editPoint = value;
    }

    /**
     * Gets the value of the usedImputationMethod property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getUsedImputationMethod() {
        return usedImputationMethod;
    }

    /**
     * Sets the value of the usedImputationMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setUsedImputationMethod(YesOrNo value) {
        this.usedImputationMethod = value;
    }

}
