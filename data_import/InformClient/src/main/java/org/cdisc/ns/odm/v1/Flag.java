
package org.cdisc.ns.odm.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Flag complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Flag">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}FlagValue"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}FlagType" minOccurs="0"/>
 *         &lt;group ref="{http://www.cdisc.org/ns/odm/v1.3}FlagElementExtension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}FlagAttributeExtension"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Flag", propOrder = {
    "flagValue",
    "flagType"
})
public class Flag {

    @XmlElement(name = "FlagValue", required = true)
    protected FlagValue flagValue;
    @XmlElement(name = "FlagType")
    protected FlagType flagType;

    /**
     * Gets the value of the flagValue property.
     * 
     * @return
     *     possible object is
     *     {@link FlagValue }
     *     
     */
    public FlagValue getFlagValue() {
        return flagValue;
    }

    /**
     * Sets the value of the flagValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagValue }
     *     
     */
    public void setFlagValue(FlagValue value) {
        this.flagValue = value;
    }

    /**
     * Gets the value of the flagType property.
     * 
     * @return
     *     possible object is
     *     {@link FlagType }
     *     
     */
    public FlagType getFlagType() {
        return flagType;
    }

    /**
     * Sets the value of the flagType property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagType }
     *     
     */
    public void setFlagType(FlagType value) {
        this.flagType = value;
    }

}
