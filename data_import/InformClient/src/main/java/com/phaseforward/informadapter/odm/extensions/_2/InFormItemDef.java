
package com.phaseforward.informadapter.odm.extensions._2;

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
import org.cdisc.ns.odm.v1.ItemRef;
import org.cdisc.ns.odm.v1.YesOrNo;


/**
 * <p>Java class for InFormItemDef complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InFormItemDef">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}ItemRef" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="OID" use="required" type="{http://www.cdisc.org/ns/odm/v1.3}oid" />
 *       &lt;attribute name="Name" use="required" type="{http://www.cdisc.org/ns/odm/v1.3}text" />
 *       &lt;attribute name="SVRequired" type="{http://www.cdisc.org/ns/odm/v1.3}YesOrNo" />
 *       &lt;attribute ref="{http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0}DBUID"/>
 *       &lt;attribute ref="{http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0}GUID"/>
 *       &lt;attribute ref="{http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0}Revision"/>
 *       &lt;attribute ref="{http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0}UUID"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InFormItemDef", propOrder = {
    "itemRef"
})
public class InFormItemDef {

    @XmlElement(name = "ItemRef", namespace = "http://www.cdisc.org/ns/odm/v1.3", required = true)
    protected List<ItemRef> itemRef;
    @XmlAttribute(name = "OID", required = true)
    protected String oid;
    @XmlAttribute(name = "Name", required = true)
    protected String name;
    @XmlAttribute(name = "SVRequired")
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

}
