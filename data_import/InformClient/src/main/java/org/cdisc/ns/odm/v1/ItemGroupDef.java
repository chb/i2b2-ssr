
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
import com.phaseforward.informadapter.odm.extensions._2.InFormItemRef;


/**
 * <p>Java class for ItemGroupDef complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemGroupDef">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}ItemRef" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}Alias" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;group ref="{http://www.cdisc.org/ns/odm/v1.3}ItemGroupDefElementExtension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}ItemGroupDefAttributeExtension"/>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}ItemGroupDefAttributeDefinition"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemGroupDef", propOrder = {
    "itemRef",
    "alias",
    "itemGroupDefElementExtension"
})
public class ItemGroupDef {

    @XmlElement(name = "ItemRef", required = true)
    protected List<ItemRef> itemRef;
    @XmlElement(name = "Alias")
    protected List<Alias> alias;
    @XmlElement(name = "InFormItemRef", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected List<InFormItemRef> itemGroupDefElementExtension;
    @XmlAttribute(name = "ItemSetRefName", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected String itemSetRefName;
    @XmlAttribute(name = "SVRequired", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected YesOrNo svRequired;
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
    @XmlAttribute(name = "IsReferenceData")
    protected YesOrNo isReferenceData;
    @XmlAttribute(name = "SASDatasetName")
    protected String sasDatasetName;
    @XmlAttribute(name = "Domain")
    protected String domain;
    @XmlAttribute(name = "Origin")
    protected String origin;
    @XmlAttribute(name = "Role")
    protected String role;
    @XmlAttribute(name = "Purpose")
    protected String purpose;
    @XmlAttribute(name = "Comment")
    protected String comment;

    /**
     * Gets the value of the itemRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemRef }
     * 
     * 
     */
    public List<ItemRef> getItemRef() {
        if (itemRef == null) {
            itemRef = new ArrayList<ItemRef>();
        }
        return this.itemRef;
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
     * Gets the value of the itemGroupDefElementExtension property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemGroupDefElementExtension property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemGroupDefElementExtension().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InFormItemRef }
     * 
     * 
     */
    public List<InFormItemRef> getItemGroupDefElementExtension() {
        if (itemGroupDefElementExtension == null) {
            itemGroupDefElementExtension = new ArrayList<InFormItemRef>();
        }
        return this.itemGroupDefElementExtension;
    }

    /**
     * Gets the value of the itemSetRefName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemSetRefName() {
        return itemSetRefName;
    }

    /**
     * Sets the value of the itemSetRefName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemSetRefName(String value) {
        this.itemSetRefName = value;
    }

    /**
     * Gets the value of the svRequired property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getSVRequired() {
        return svRequired;
    }

    /**
     * Sets the value of the svRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setSVRequired(YesOrNo value) {
        this.svRequired = value;
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
     * Gets the value of the isReferenceData property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getIsReferenceData() {
        return isReferenceData;
    }

    /**
     * Sets the value of the isReferenceData property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setIsReferenceData(YesOrNo value) {
        this.isReferenceData = value;
    }

    /**
     * Gets the value of the sasDatasetName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSASDatasetName() {
        return sasDatasetName;
    }

    /**
     * Sets the value of the sasDatasetName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSASDatasetName(String value) {
        this.sasDatasetName = value;
    }

    /**
     * Gets the value of the domain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the value of the domain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomain(String value) {
        this.domain = value;
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
     * Gets the value of the role property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRole(String value) {
        this.role = value;
    }

    /**
     * Gets the value of the purpose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Sets the value of the purpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurpose(String value) {
        this.purpose = value;
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

}
