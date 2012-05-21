
package org.cdisc.ns.odm.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import com.phaseforward.informadapter.odm.extensions._2.ItemAuditData;
import com.phaseforward.informadapter.odm.extensions._2.ReasonIncomplete;


/**
 * <p>Java class for ItemData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}AuditRecord" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}Signature" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}MeasurementUnitRef" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}Annotation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;group ref="{http://www.cdisc.org/ns/odm/v1.3}ItemDataElementExtension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}ItemDataAttributeDefinition"/>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}ItemDataAttributeExtension"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemData", propOrder = {
    "auditRecord",
    "signature",
    "measurementUnitRef",
    "annotation",
    "itemDataElementExtension"
})
public class ItemData {

    @XmlElement(name = "AuditRecord")
    protected AuditRecord auditRecord;
    @XmlElement(name = "Signature")
    protected Signature signature;
    @XmlElement(name = "MeasurementUnitRef")
    protected MeasurementUnitRef measurementUnitRef;
    @XmlElement(name = "Annotation")
    protected List<Annotation> annotation;
    @XmlElements({
        @XmlElement(name = "ReasonIncomplete", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0", type = ReasonIncomplete.class),
        @XmlElement(name = "ItemAuditData", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0", type = ItemAuditData.class)
    })
    protected List<Object> itemDataElementExtension;
    @XmlAttribute(name = "ItemOID", required = true)
    protected String itemOID;
    @XmlAttribute(name = "TransactionType")
    protected TransactionType transactionType;
    @XmlAttribute(name = "Value")
    protected String value;
    @XmlAttribute(name = "IsNull")
    protected YesOnly isNull;
    @XmlAttribute(name = "NormalizedValue", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected String normalizedValue;
    @XmlAttribute(name = "FormattedDateValue", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected String formattedDateValue;

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
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link Signature }
     *     
     */
    public Signature getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link Signature }
     *     
     */
    public void setSignature(Signature value) {
        this.signature = value;
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
     * Gets the value of the annotation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the annotation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnnotation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Annotation }
     * 
     * 
     */
    public List<Annotation> getAnnotation() {
        if (annotation == null) {
            annotation = new ArrayList<Annotation>();
        }
        return this.annotation;
    }

    /**
     * Gets the value of the itemDataElementExtension property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemDataElementExtension property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemDataElementExtension().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReasonIncomplete }
     * {@link ItemAuditData }
     * 
     * 
     */
    public List<Object> getItemDataElementExtension() {
        if (itemDataElementExtension == null) {
            itemDataElementExtension = new ArrayList<Object>();
        }
        return this.itemDataElementExtension;
    }

    /**
     * Gets the value of the itemOID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemOID() {
        return itemOID;
    }

    /**
     * Sets the value of the itemOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemOID(String value) {
        this.itemOID = value;
    }

    /**
     * Gets the value of the transactionType property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionType }
     *     
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the value of the transactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionType }
     *     
     */
    public void setTransactionType(TransactionType value) {
        this.transactionType = value;
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

    /**
     * Gets the value of the isNull property.
     * 
     * @return
     *     possible object is
     *     {@link YesOnly }
     *     
     */
    public YesOnly getIsNull() {
        return isNull;
    }

    /**
     * Sets the value of the isNull property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOnly }
     *     
     */
    public void setIsNull(YesOnly value) {
        this.isNull = value;
    }

    /**
     * Gets the value of the normalizedValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNormalizedValue() {
        return normalizedValue;
    }

    /**
     * Sets the value of the normalizedValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNormalizedValue(String value) {
        this.normalizedValue = value;
    }

    /**
     * Gets the value of the formattedDateValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormattedDateValue() {
        return formattedDateValue;
    }

    /**
     * Sets the value of the formattedDateValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormattedDateValue(String value) {
        this.formattedDateValue = value;
    }

}
