
package org.cdisc.ns.odm.v1;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.phaseforward.informadapter.odm.extensions._2.Mnemonic;


/**
 * <p>Java class for StudyEventDef complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StudyEventDef">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}FormRef" maxOccurs="unbounded"/>
 *         &lt;group ref="{http://www.cdisc.org/ns/odm/v1.3}StudyEventDefElementExtension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}StudyEventDefAttributeExtension"/>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}StudyEventDefAttributeDefinition"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudyEventDef", propOrder = {
    "formRef",
    "studyEventDefElementExtension"
})
public class StudyEventDef {

    @XmlElement(name = "FormRef", required = true)
    protected List<FormRef> formRef;
    @XmlElement(name = "Mnemonic", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected List<Mnemonic> studyEventDefElementExtension;
    @XmlAttribute(name = "Dynamic", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected YesOrNo dynamic;
    @XmlAttribute(name = "Optional", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected YesOrNo optional;
    @XmlAttribute(name = "DBUID", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger dbuid;
    @XmlAttribute(name = "GUID", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected String guid;
    @XmlAttribute(name = "Revision", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected BigDecimal revision;
    @XmlAttribute(name = "UUID", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected String uuid;
    @XmlAttribute(name = "OID", required = true)
    protected String oid;
    @XmlAttribute(name = "Name", required = true)
    protected String name;
    @XmlAttribute(name = "Repeating", required = true)
    protected YesOrNo repeating;
    @XmlAttribute(name = "Type", required = true)
    protected EventType type;
    @XmlAttribute(name = "Category")
    protected String category;

    /**
     * Gets the value of the formRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the formRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FormRef }
     * 
     * 
     */
    public List<FormRef> getFormRef() {
        if (formRef == null) {
            formRef = new ArrayList<FormRef>();
        }
        return this.formRef;
    }

    /**
     * Gets the value of the studyEventDefElementExtension property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the studyEventDefElementExtension property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStudyEventDefElementExtension().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Mnemonic }
     * 
     * 
     */
    public List<Mnemonic> getStudyEventDefElementExtension() {
        if (studyEventDefElementExtension == null) {
            studyEventDefElementExtension = new ArrayList<Mnemonic>();
        }
        return this.studyEventDefElementExtension;
    }

    /**
     * Gets the value of the dynamic property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getDynamic() {
        return dynamic;
    }

    /**
     * Sets the value of the dynamic property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setDynamic(YesOrNo value) {
        this.dynamic = value;
    }

    /**
     * Gets the value of the optional property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getOptional() {
        return optional;
    }

    /**
     * Sets the value of the optional property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setOptional(YesOrNo value) {
        this.optional = value;
    }

    /**
     * Gets the value of the dbuid property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDBUID() {
        return dbuid;
    }

    /**
     * Sets the value of the dbuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDBUID(BigInteger value) {
        this.dbuid = value;
    }

    /**
     * Gets the value of the guid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGUID() {
        return guid;
    }

    /**
     * Sets the value of the guid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGUID(String value) {
        this.guid = value;
    }

    /**
     * Gets the value of the revision property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRevision() {
        return revision;
    }

    /**
     * Sets the value of the revision property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRevision(BigDecimal value) {
        this.revision = value;
    }

    /**
     * Gets the value of the uuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUUID() {
        return uuid;
    }

    /**
     * Sets the value of the uuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUUID(String value) {
        this.uuid = value;
    }

    /**
     * Gets the value of the oid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOID() {
        return oid;
    }

    /**
     * Sets the value of the oid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOID(String value) {
        this.oid = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the repeating property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getRepeating() {
        return repeating;
    }

    /**
     * Sets the value of the repeating property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setRepeating(YesOrNo value) {
        this.repeating = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link EventType }
     *     
     */
    public EventType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventType }
     *     
     */
    public void setType(EventType value) {
        this.type = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategory(String value) {
        this.category = value;
    }

}
