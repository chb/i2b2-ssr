
package org.cdisc.ns.odm.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Signature complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Signature">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}UserRef"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}LocationRef"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}SignatureRef"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}DateTimeStamp"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}CryptoBindingManifest" minOccurs="0"/>
 *         &lt;group ref="{http://www.cdisc.org/ns/odm/v1.3}SignatureElementExtension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}SignatureAttributeExtension"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Signature", propOrder = {
    "userRef",
    "locationRef",
    "signatureRef",
    "dateTimeStamp",
    "cryptoBindingManifest"
})
public class Signature {

    @XmlElement(name = "UserRef", required = true)
    protected UserRef userRef;
    @XmlElement(name = "LocationRef", required = true)
    protected LocationRef locationRef;
    @XmlElement(name = "SignatureRef", required = true)
    protected SignatureRef signatureRef;
    @XmlElement(name = "DateTimeStamp", required = true)
    protected DateTimeStamp dateTimeStamp;
    @XmlElement(name = "CryptoBindingManifest")
    protected CryptoBindingManifest cryptoBindingManifest;

    /**
     * Gets the value of the userRef property.
     * 
     * @return
     *     possible object is
     *     {@link UserRef }
     *     
     */
    public UserRef getUserRef() {
        return userRef;
    }

    /**
     * Sets the value of the userRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserRef }
     *     
     */
    public void setUserRef(UserRef value) {
        this.userRef = value;
    }

    /**
     * Gets the value of the locationRef property.
     * 
     * @return
     *     possible object is
     *     {@link LocationRef }
     *     
     */
    public LocationRef getLocationRef() {
        return locationRef;
    }

    /**
     * Sets the value of the locationRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationRef }
     *     
     */
    public void setLocationRef(LocationRef value) {
        this.locationRef = value;
    }

    /**
     * Gets the value of the signatureRef property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureRef }
     *     
     */
    public SignatureRef getSignatureRef() {
        return signatureRef;
    }

    /**
     * Sets the value of the signatureRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureRef }
     *     
     */
    public void setSignatureRef(SignatureRef value) {
        this.signatureRef = value;
    }

    /**
     * Gets the value of the dateTimeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link DateTimeStamp }
     *     
     */
    public DateTimeStamp getDateTimeStamp() {
        return dateTimeStamp;
    }

    /**
     * Sets the value of the dateTimeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTimeStamp }
     *     
     */
    public void setDateTimeStamp(DateTimeStamp value) {
        this.dateTimeStamp = value;
    }

    /**
     * Gets the value of the cryptoBindingManifest property.
     * 
     * @return
     *     possible object is
     *     {@link CryptoBindingManifest }
     *     
     */
    public CryptoBindingManifest getCryptoBindingManifest() {
        return cryptoBindingManifest;
    }

    /**
     * Sets the value of the cryptoBindingManifest property.
     * 
     * @param value
     *     allowed object is
     *     {@link CryptoBindingManifest }
     *     
     */
    public void setCryptoBindingManifest(CryptoBindingManifest value) {
        this.cryptoBindingManifest = value;
    }

}
