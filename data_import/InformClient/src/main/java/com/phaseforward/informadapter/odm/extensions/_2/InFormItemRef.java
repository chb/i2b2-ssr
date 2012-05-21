
package com.phaseforward.informadapter.odm.extensions._2;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import org.cdisc.ns.odm.v1.YesOrNo;


/**
 * <p>Java class for InFormItemRef complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InFormItemRef">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="InFormItemOId" use="required" type="{http://www.cdisc.org/ns/odm/v1.3}oid" />
 *       &lt;attribute name="OrderNumber" type="{http://www.cdisc.org/ns/odm/v1.3}integer" />
 *       &lt;attribute name="Mandatory" use="required" type="{http://www.cdisc.org/ns/odm/v1.3}YesOrNo" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InFormItemRef")
public class InFormItemRef {

    @XmlAttribute(name = "InFormItemOId", required = true)
    protected String inFormItemOId;
    @XmlAttribute(name = "OrderNumber")
    protected BigInteger orderNumber;
    @XmlAttribute(name = "Mandatory", required = true)
    protected YesOrNo mandatory;

    /**
     * Gets the value of the inFormItemOId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInFormItemOId() {
        return inFormItemOId;
    }

    /**
     * Sets the value of the inFormItemOId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInFormItemOId(String value) {
        this.inFormItemOId = value;
    }

    /**
     * Gets the value of the orderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOrderNumber() {
        return orderNumber;
    }

    /**
     * Sets the value of the orderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOrderNumber(BigInteger value) {
        this.orderNumber = value;
    }

    /**
     * Gets the value of the mandatory property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getMandatory() {
        return mandatory;
    }

    /**
     * Sets the value of the mandatory property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setMandatory(YesOrNo value) {
        this.mandatory = value;
    }

}
