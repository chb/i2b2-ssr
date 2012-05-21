
package com.phaseforward.informadapter.odm.extensions._2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GroupRef complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GroupRef">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attGroup ref="{http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0}GroupRefAttributeDefinition"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroupRef")
public class GroupRef {

    @XmlAttribute(name = "GroupOID", required = true)
    protected String groupOID;

    /**
     * Gets the value of the groupOID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupOID() {
        return groupOID;
    }

    /**
     * Sets the value of the groupOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupOID(String value) {
        this.groupOID = value;
    }

}
