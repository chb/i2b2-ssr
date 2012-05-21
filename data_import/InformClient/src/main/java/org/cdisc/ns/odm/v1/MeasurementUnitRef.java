
package org.cdisc.ns.odm.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MeasurementUnitRef complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MeasurementUnitRef">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{http://www.cdisc.org/ns/odm/v1.3}MeasurementUnitRefElementExtension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}MeasurementUnitRefAttributeExtension"/>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}MeasurementUnitRefAttributeDefinition"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeasurementUnitRef")
public class MeasurementUnitRef {

    @XmlAttribute(name = "NormalizationUnit", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected YesOrNo normalizationUnit;
    @XmlAttribute(name = "MeasurementUnitOID", required = true)
    protected String measurementUnitOID;

    /**
     * Gets the value of the normalizationUnit property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getNormalizationUnit() {
        return normalizationUnit;
    }

    /**
     * Sets the value of the normalizationUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setNormalizationUnit(YesOrNo value) {
        this.normalizationUnit = value;
    }

    /**
     * Gets the value of the measurementUnitOID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasurementUnitOID() {
        return measurementUnitOID;
    }

    /**
     * Sets the value of the measurementUnitOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasurementUnitOID(String value) {
        this.measurementUnitOID = value;
    }

}
