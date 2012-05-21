
package org.cdisc.ns.odm.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import com.phaseforward.informadapter.odm.extensions._2.FormAuditData;
import com.phaseforward.informadapter.odm.extensions._2.FormStatus;
import com.phaseforward.informadapter.odm.extensions._2.PFFormSignatures;


/**
 * <p>Java class for FormData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FormData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}AuditRecord" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}Signature" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}ArchiveLayoutRef" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}Annotation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}ItemGroupData" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;group ref="{http://www.cdisc.org/ns/odm/v1.3}FormDataElementExtension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}FormDataAttributeDefinition"/>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}FormDataAttributeExtension"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FormData", propOrder = {
    "auditRecord",
    "signature",
    "archiveLayoutRef",
    "annotation",
    "itemGroupData",
    "formDataElementExtension"
})
public class FormData {

    @XmlElement(name = "AuditRecord")
    protected AuditRecord auditRecord;
    @XmlElement(name = "Signature")
    protected Signature signature;
    @XmlElement(name = "ArchiveLayoutRef")
    protected ArchiveLayoutRef archiveLayoutRef;
    @XmlElement(name = "Annotation")
    protected List<Annotation> annotation;
    @XmlElement(name = "ItemGroupData")
    protected List<ItemGroupData> itemGroupData;
    @XmlElements({
        @XmlElement(name = "FormStatus", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0", type = FormStatus.class),
        @XmlElement(name = "FormAuditData", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0", type = FormAuditData.class),
        @XmlElement(name = "PFFormSignatures", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0", type = PFFormSignatures.class)
    })
    protected List<Object> formDataElementExtension;
    @XmlAttribute(name = "FormOID", required = true)
    protected String formOID;
    @XmlAttribute(name = "FormRepeatKey")
    protected String formRepeatKey;
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
     * Gets the value of the archiveLayoutRef property.
     * 
     * @return
     *     possible object is
     *     {@link ArchiveLayoutRef }
     *     
     */
    public ArchiveLayoutRef getArchiveLayoutRef() {
        return archiveLayoutRef;
    }

    /**
     * Sets the value of the archiveLayoutRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArchiveLayoutRef }
     *     
     */
    public void setArchiveLayoutRef(ArchiveLayoutRef value) {
        this.archiveLayoutRef = value;
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
     * Gets the value of the itemGroupData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemGroupData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemGroupData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemGroupData }
     * 
     * 
     */
    public List<ItemGroupData> getItemGroupData() {
        if (itemGroupData == null) {
            itemGroupData = new ArrayList<ItemGroupData>();
        }
        return this.itemGroupData;
    }

    /**
     * Gets the value of the formDataElementExtension property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the formDataElementExtension property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormDataElementExtension().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FormStatus }
     * {@link FormAuditData }
     * {@link PFFormSignatures }
     * 
     * 
     */
    public List<Object> getFormDataElementExtension() {
        if (formDataElementExtension == null) {
            formDataElementExtension = new ArrayList<Object>();
        }
        return this.formDataElementExtension;
    }

    /**
     * Gets the value of the formOID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormOID() {
        return formOID;
    }

    /**
     * Sets the value of the formOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormOID(String value) {
        this.formOID = value;
    }

    /**
     * Gets the value of the formRepeatKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormRepeatKey() {
        return formRepeatKey;
    }

    /**
     * Sets the value of the formRepeatKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormRepeatKey(String value) {
        this.formRepeatKey = value;
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
