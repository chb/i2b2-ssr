
package org.cdisc.ns.odm.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RangeCheck complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RangeCheck">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}CheckValue" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}MeasurementUnitRef" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}ErrorMessage" minOccurs="0"/>
 *         &lt;group ref="{http://www.cdisc.org/ns/odm/v1.3}RangeCheckElementExtension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}RangeCheckAttributeExtension"/>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}RangeCheckAttributeDefinition"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RangeCheck", propOrder = {
    "checkValue",
    "measurementUnitRef",
    "errorMessage"
})
public class RangeCheck {

    @XmlElement(name = "CheckValue", required = true)
    protected List<CheckValue> checkValue;
    @XmlElement(name = "MeasurementUnitRef")
    protected MeasurementUnitRef measurementUnitRef;
    @XmlElement(name = "ErrorMessage")
    protected ErrorMessage errorMessage;
    @XmlAttribute(name = "Comparator", required = true)
    protected Comparator comparator;
    @XmlAttribute(name = "SoftHard", required = true)
    protected SoftOrHard softHard;

    /**
     * Gets the value of the checkValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the checkValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCheckValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CheckValue }
     * 
     * 
     */
    public List<CheckValue> getCheckValue() {
        if (checkValue == null) {
            checkValue = new ArrayList<CheckValue>();
        }
        return this.checkValue;
    }

    /**
     * Gets the value of the measurementUnitRef property.
     * 
     * @return
     *     possible object is
     *     {@link MeasurementUnitRef }
     *     
     */
    public MeasurementUnitRef getMeasurementUnitRef() {
        return measurementUnitRef;
    }

    /**
     * Sets the value of the measurementUnitRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeasurementUnitRef }
     *     
     */
    public void setMeasurementUnitRef(MeasurementUnitRef value) {
        this.measurementUnitRef = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorMessage }
     *     
     */
    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorMessage }
     *     
     */
    public void setErrorMessage(ErrorMessage value) {
        this.errorMessage = value;
    }

    /**
     * Gets the value of the comparator property.
     * 
     * @return
     *     possible object is
     *     {@link Comparator }
     *     
     */
    public Comparator getComparator() {
        return comparator;
    }

    /**
     * Sets the value of the comparator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comparator }
     *     
     */
    public void setComparator(Comparator value) {
        this.comparator = value;
    }

    /**
     * Gets the value of the softHard property.
     * 
     * @return
     *     possible object is
     *     {@link SoftOrHard }
     *     
     */
    public SoftOrHard getSoftHard() {
        return softHard;
    }

    /**
     * Sets the value of the softHard property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoftOrHard }
     *     
     */
    public void setSoftHard(SoftOrHard value) {
        this.softHard = value;
    }

}
