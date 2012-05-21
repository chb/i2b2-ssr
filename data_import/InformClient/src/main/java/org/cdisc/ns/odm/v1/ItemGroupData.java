
package org.cdisc.ns.odm.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import com.phaseforward.informadapter.odm.extensions._2.InFormItemData;
import com.phaseforward.informadapter.odm.extensions._2.ItemStatus;
import com.phaseforward.informadapter.odm.extensions._2.Query;


/**
 * <p>Java class for ItemGroupData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemGroupData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}AuditRecord" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}Signature" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}Annotation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}ItemData" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;group ref="{http://www.cdisc.org/ns/odm/v1.3}ItemGroupDataElementExtension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}ItemGroupDataAttributeDefinition"/>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}ItemGroupDataAttributeExtension"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemGroupData", propOrder = {
    "auditRecord",
    "signature",
    "annotation",
    "itemData",
    "itemGroupDataElementExtension"
})
public class ItemGroupData {

    @XmlElement(name = "AuditRecord")
    protected AuditRecord auditRecord;
    @XmlElement(name = "Signature")
    protected Signature signature;
    @XmlElement(name = "Annotation")
    protected List<Annotation> annotation;
    @XmlElement(name = "ItemData")
    protected List<ItemData> itemData;
    @XmlElements({
        @XmlElement(name = "Query", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0", type = Query.class),
        @XmlElement(name = "ItemStatus", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0", type = ItemStatus.class),
        @XmlElement(name = "InFormItemData", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0", type = InFormItemData.class)
    })
    protected List<Object> itemGroupDataElementExtension;
    @XmlAttribute(name = "ItemGroupOID", required = true)
    protected String itemGroupOID;
    @XmlAttribute(name = "ItemGroupRepeatKey")
    protected String itemGroupRepeatKey;
    @XmlAttribute(name = "TransactionType")
    protected TransactionType transactionType;

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
     * Gets the value of the itemData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemData }
     * 
     * 
     */
    public List<ItemData> getItemData() {
        if (itemData == null) {
            itemData = new ArrayList<ItemData>();
        }
        return this.itemData;
    }

    /**
     * Gets the value of the itemGroupDataElementExtension property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemGroupDataElementExtension property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemGroupDataElementExtension().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Query }
     * {@link ItemStatus }
     * {@link InFormItemData }
     * 
     * 
     */
    public List<Object> getItemGroupDataElementExtension() {
        if (itemGroupDataElementExtension == null) {
            itemGroupDataElementExtension = new ArrayList<Object>();
        }
        return this.itemGroupDataElementExtension;
    }

    /**
     * Gets the value of the itemGroupOID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemGroupOID() {
        return itemGroupOID;
    }

    /**
     * Sets the value of the itemGroupOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemGroupOID(String value) {
        this.itemGroupOID = value;
    }

    /**
     * Gets the value of the itemGroupRepeatKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemGroupRepeatKey() {
        return itemGroupRepeatKey;
    }

    /**
     * Sets the value of the itemGroupRepeatKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemGroupRepeatKey(String value) {
        this.itemGroupRepeatKey = value;
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

}
