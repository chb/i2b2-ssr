
package com.phaseforward.informadapter.odm.extensions._2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserRef complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserRef">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="UserOID" use="required" type="{http://www.cdisc.org/ns/odm/v1.3}oidref" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserRef")
public class UserRef {

    @XmlAttribute(name = "UserOID", required = true)
    protected String userOID;

    /**
     * Gets the value of the userOID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserOID() {
        return userOID;
    }

    /**
     * Sets the value of the userOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserOID(String value) {
        this.userOID = value;
    }

}
