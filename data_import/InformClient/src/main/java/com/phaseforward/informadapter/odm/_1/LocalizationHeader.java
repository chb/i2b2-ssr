
package com.phaseforward.informadapter.odm._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LocalizationHeader complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LocalizationHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CultureName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocalizationHeader", propOrder = {
    "cultureName"
})
public class LocalizationHeader {

    @XmlElement(name = "CultureName")
    protected String cultureName;

    /**
     * Gets the value of the cultureName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCultureName() {
        return cultureName;
    }

    /**
     * Sets the value of the cultureName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCultureName(String value) {
        this.cultureName = value;
    }

}
