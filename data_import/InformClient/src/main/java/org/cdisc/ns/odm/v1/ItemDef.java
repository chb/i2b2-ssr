
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
import com.phaseforward.informadapter.odm.extensions._2.ItemDefType;
import com.phaseforward.informadapter.odm.extensions._2.ParentType;


/**
 * <p>Java class for ItemDef complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemDef">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}Question" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}ExternalQuestion" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}MeasurementUnitRef" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}RangeCheck" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}CodeListRef" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}Role" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}Alias" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;group ref="{http://www.cdisc.org/ns/odm/v1.3}ItemDefElementExtension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}ItemDefAttributeDefinition"/>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}ItemDefAttributeExtension"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemDef", propOrder = {
    "question",
    "externalQuestion",
    "measurementUnitRef",
    "rangeCheck",
    "codeListRef",
    "role",
    "alias"
})
public class ItemDef {

    @XmlElement(name = "Question")
    protected Question question;
    @XmlElement(name = "ExternalQuestion")
    protected ExternalQuestion externalQuestion;
    @XmlElement(name = "MeasurementUnitRef")
    protected List<MeasurementUnitRef> measurementUnitRef;
    @XmlElement(name = "RangeCheck")
    protected List<RangeCheck> rangeCheck;
    @XmlElement(name = "CodeListRef")
    protected CodeListRef codeListRef;
    @XmlElement(name = "Role")
    protected List<Role> role;
    @XmlElement(name = "Alias")
    protected List<Alias> alias;
    @XmlAttribute(name = "OID", required = true)
    protected String oid;
    @XmlAttribute(name = "Name", required = true)
    protected String name;
    @XmlAttribute(name = "DataType", required = true)
    protected DataType dataType;
    @XmlAttribute(name = "Length")
    protected BigInteger length;
    @XmlAttribute(name = "SignificantDigits")
    protected BigInteger significantDigits;
    @XmlAttribute(name = "SASFieldName")
    protected String sasFieldName;
    @XmlAttribute(name = "SDSVarName")
    protected String sdsVarName;
    @XmlAttribute(name = "Origin")
    protected String origin;
    @XmlAttribute(name = "Comment")
    protected String comment;
    @XmlAttribute(name = "CheckboxGroupRefName", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected String checkboxGroupRefName;
    @XmlAttribute(name = "InFormDateTime", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected Boolean inFormDateTime;
    @XmlAttribute(name = "PFVarName", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected String pfVarName;
    @XmlAttribute(name = "DBUID", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger dbuid;
    @XmlAttribute(name = "GUID", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected String guid;
    @XmlAttribute(name = "Revision", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected BigDecimal revision;
    @XmlAttribute(name = "ItemDefType", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected ItemDefType itemDefType;
    @XmlAttribute(name = "ParentOID", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected String parentOID;
    @XmlAttribute(name = "ParentType", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected ParentType parentType;
    @XmlAttribute(name = "SelectionOwner", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected String selectionOwner;
    @XmlAttribute(name = "UUID", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected String uuid;

    /**
     * Gets the value of the question property.
     * 
     * @return
     *     possible object is
     *     {@link Question }
     *     
     */
    public Question getQuestion() {
        return question;
    }

    /**
     * Sets the value of the question property.
     * 
     * @param value
     *     allowed object is
     *     {@link Question }
     *     
     */
    public void setQuestion(Question value) {
        this.question = value;
    }

    /**
     * Gets the value of the externalQuestion property.
     * 
     * @return
     *     possible object is
     *     {@link ExternalQuestion }
     *     
     */
    public ExternalQuestion getExternalQuestion() {
        return externalQuestion;
    }

    /**
     * Sets the value of the externalQuestion property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExternalQuestion }
     *     
     */
    public void setExternalQuestion(ExternalQuestion value) {
        this.externalQuestion = value;
    }

    /**
     * Gets the value of the measurementUnitRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the measurementUnitRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMeasurementUnitRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MeasurementUnitRef }
     * 
     * 
     */
    public List<MeasurementUnitRef> getMeasurementUnitRef() {
        if (measurementUnitRef == null) {
            measurementUnitRef = new ArrayList<MeasurementUnitRef>();
        }
        return this.measurementUnitRef;
    }

    /**
     * Gets the value of the rangeCheck property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rangeCheck property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRangeCheck().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RangeCheck }
     * 
     * 
     */
    public List<RangeCheck> getRangeCheck() {
        if (rangeCheck == null) {
            rangeCheck = new ArrayList<RangeCheck>();
        }
        return this.rangeCheck;
    }

    /**
     * Gets the value of the codeListRef property.
     * 
     * @return
     *     possible object is
     *     {@link CodeListRef }
     *     
     */
    public CodeListRef getCodeListRef() {
        return codeListRef;
    }

    /**
     * Sets the value of the codeListRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeListRef }
     *     
     */
    public void setCodeListRef(CodeListRef value) {
        this.codeListRef = value;
    }

    /**
     * Gets the value of the role property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the role property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Role }
     * 
     * 
     */
    public List<Role> getRole() {
        if (role == null) {
            role = new ArrayList<Role>();
        }
        return this.role;
    }

    /**
     * Gets the value of the alias property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the alias property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlias().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Alias }
     * 
     * 
     */
    public List<Alias> getAlias() {
        if (alias == null) {
            alias = new ArrayList<Alias>();
        }
        return this.alias;
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
     * Gets the value of the dataType property.
     * 
     * @return
     *     possible object is
     *     {@link DataType }
     *     
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * Sets the value of the dataType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataType }
     *     
     */
    public void setDataType(DataType value) {
        this.dataType = value;
    }

    /**
     * Gets the value of the length property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLength() {
        return length;
    }

    /**
     * Sets the value of the length property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLength(BigInteger value) {
        this.length = value;
    }

    /**
     * Gets the value of the significantDigits property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSignificantDigits() {
        return significantDigits;
    }

    /**
     * Sets the value of the significantDigits property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSignificantDigits(BigInteger value) {
        this.significantDigits = value;
    }

    /**
     * Gets the value of the sasFieldName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSASFieldName() {
        return sasFieldName;
    }

    /**
     * Sets the value of the sasFieldName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSASFieldName(String value) {
        this.sasFieldName = value;
    }

    /**
     * Gets the value of the sdsVarName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSDSVarName() {
        return sdsVarName;
    }

    /**
     * Sets the value of the sdsVarName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSDSVarName(String value) {
        this.sdsVarName = value;
    }

    /**
     * Gets the value of the origin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets the value of the origin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigin(String value) {
        this.origin = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the checkboxGroupRefName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckboxGroupRefName() {
        return checkboxGroupRefName;
    }

    /**
     * Sets the value of the checkboxGroupRefName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckboxGroupRefName(String value) {
        this.checkboxGroupRefName = value;
    }

    /**
     * Gets the value of the inFormDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInFormDateTime() {
        return inFormDateTime;
    }

    /**
     * Sets the value of the inFormDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInFormDateTime(Boolean value) {
        this.inFormDateTime = value;
    }

    /**
     * Gets the value of the pfVarName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPFVarName() {
        return pfVarName;
    }

    /**
     * Sets the value of the pfVarName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPFVarName(String value) {
        this.pfVarName = value;
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
     * Gets the value of the itemDefType property.
     * 
     * @return
     *     possible object is
     *     {@link ItemDefType }
     *     
     */
    public ItemDefType getItemDefType() {
        return itemDefType;
    }

    /**
     * Sets the value of the itemDefType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemDefType }
     *     
     */
    public void setItemDefType(ItemDefType value) {
        this.itemDefType = value;
    }

    /**
     * Gets the value of the parentOID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentOID() {
        return parentOID;
    }

    /**
     * Sets the value of the parentOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentOID(String value) {
        this.parentOID = value;
    }

    /**
     * Gets the value of the parentType property.
     * 
     * @return
     *     possible object is
     *     {@link ParentType }
     *     
     */
    public ParentType getParentType() {
        return parentType;
    }

    /**
     * Sets the value of the parentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParentType }
     *     
     */
    public void setParentType(ParentType value) {
        this.parentType = value;
    }

    /**
     * Gets the value of the selectionOwner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelectionOwner() {
        return selectionOwner;
    }

    /**
     * Sets the value of the selectionOwner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelectionOwner(String value) {
        this.selectionOwner = value;
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

}
