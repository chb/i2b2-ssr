
package org.cdisc.ns.odm.v1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.phaseforward.informadapter.odm.extensions._2.Mnemonic;


/**
 * <p>Java class for Location complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Location">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}MetaDataVersionRef" maxOccurs="unbounded"/>
 *         &lt;group ref="{http://www.cdisc.org/ns/odm/v1.3}LocationElementExtension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}LocationAttributeDefinition"/>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}LocationAttributeExtension"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Location", propOrder = {
    "metaDataVersionRef",
    "locationElementExtension"
})
public class Location {

    @XmlElement(name = "MetaDataVersionRef", required = true)
    protected List<MetaDataVersionRef> metaDataVersionRef;
    @XmlElement(name = "Mnemonic", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected List<Mnemonic> locationElementExtension;
    @XmlAttribute(name = "OID", required = true)
    protected String oid;
    @XmlAttribute(name = "Name", required = true)
    protected String name;
    @XmlAttribute(name = "LocationType")
    protected LocationType locationType;
    @XmlAttribute(name = "Mnemonic", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected String mnemonic;
    @XmlAttribute(name = "SiteCountry", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected String siteCountry;
    @XmlAttribute(name = "SiteTimezone", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected String siteTimezone;
    @XmlAttribute(name = "GUID", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected String guid;
    @XmlAttribute(name = "Revision", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected BigDecimal revision;
    @XmlAttribute(name = "Locale", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "language")
    protected String locale;

    /**
     * Gets the value of the metaDataVersionRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the metaDataVersionRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMetaDataVersionRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MetaDataVersionRef }
     * 
     * 
     */
    public List<MetaDataVersionRef> getMetaDataVersionRef() {
        if (metaDataVersionRef == null) {
            metaDataVersionRef = new ArrayList<MetaDataVersionRef>();
        }
        return this.metaDataVersionRef;
    }

    /**
     * Gets the value of the locationElementExtension property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the locationElementExtension property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocationElementExtension().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Mnemonic }
     * 
     * 
     */
    public List<Mnemonic> getLocationElementExtension() {
        if (locationElementExtension == null) {
            locationElementExtension = new ArrayList<Mnemonic>();
        }
        return this.locationElementExtension;
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
     * Gets the value of the locationType property.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getLocationType() {
        return locationType;
    }

    /**
     * Sets the value of the locationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setLocationType(LocationType value) {
        this.locationType = value;
    }

    /**
     * Gets the value of the mnemonic property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMnemonic() {
        return mnemonic;
    }

    /**
     * Sets the value of the mnemonic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMnemonic(String value) {
        this.mnemonic = value;
    }

    /**
     * Gets the value of the siteCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteCountry() {
        return siteCountry;
    }

    /**
     * Sets the value of the siteCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteCountry(String value) {
        this.siteCountry = value;
    }

    /**
     * Gets the value of the siteTimezone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteTimezone() {
        return siteTimezone;
    }

    /**
     * Sets the value of the siteTimezone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteTimezone(String value) {
        this.siteTimezone = value;
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
     * Gets the value of the locale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets the value of the locale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocale(String value) {
        this.locale = value;
    }

}
