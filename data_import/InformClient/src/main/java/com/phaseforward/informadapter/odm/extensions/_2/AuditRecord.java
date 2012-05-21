
package com.phaseforward.informadapter.odm.extensions._2;

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
 *         &lt;element ref="{http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0}UserRef"/>
 *         &lt;element ref="{http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0}LocationRef"/>
 *         &lt;element ref="{http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0}DateTimeStamp"/>
 *         &lt;element ref="{http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0}ReasonForChange" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Value" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    "reasonForChange"
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
    @XmlAttribute(name = "Value")
    protected String value;

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
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

}
