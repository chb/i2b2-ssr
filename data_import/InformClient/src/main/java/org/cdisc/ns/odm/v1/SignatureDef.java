
package org.cdisc.ns.odm.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SignatureDef complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SignatureDef">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}Meaning"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}LegalReason"/>
 *         &lt;group ref="{http://www.cdisc.org/ns/odm/v1.3}SignatureDefElementExtension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}SignatureDefAttributeDefinition"/>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}SignatureDefAttributeExtension"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignatureDef", propOrder = {
    "meaning",
    "legalReason",
    "signatureDefElementExtension"
})
public class SignatureDef {

    @XmlElement(name = "Meaning", required = true)
    protected org.cdisc.ns.odm.v1.Meaning meaning;
    @XmlElement(name = "LegalReason", required = true)
    protected org.cdisc.ns.odm.v1.LegalReason legalReason;
    @XmlElements({
        @XmlElement(name = "LegalReason", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0", type = com.phaseforward.informadapter.odm.extensions._2.LegalReason.class),
        @XmlElement(name = "Meaning", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0", type = com.phaseforward.informadapter.odm.extensions._2.Meaning.class)
    })
    protected List<Object> signatureDefElementExtension;
    @XmlAttribute(name = "OID", required = true)
    protected String oid;
    @XmlAttribute(name = "Methodology")
    protected SignMethod methodology;
    @XmlAttribute(name = "GroupName", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected String groupName;

    /**
     * Gets the value of the meaning property.
     * 
     * @return
     *     possible object is
     *     {@link org.cdisc.ns.odm.v1.Meaning }
     *     
     */
    public org.cdisc.ns.odm.v1.Meaning getMeaning() {
        return meaning;
    }

    /**
     * Sets the value of the meaning property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.cdisc.ns.odm.v1.Meaning }
     *     
     */
    public void setMeaning(org.cdisc.ns.odm.v1.Meaning value) {
        this.meaning = value;
    }

    /**
     * Gets the value of the legalReason property.
     * 
     * @return
     *     possible object is
     *     {@link org.cdisc.ns.odm.v1.LegalReason }
     *     
     */
    public org.cdisc.ns.odm.v1.LegalReason getLegalReason() {
        return legalReason;
    }

    /**
     * Sets the value of the legalReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.cdisc.ns.odm.v1.LegalReason }
     *     
     */
    public void setLegalReason(org.cdisc.ns.odm.v1.LegalReason value) {
        this.legalReason = value;
    }

    /**
     * Gets the value of the signatureDefElementExtension property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signatureDefElementExtension property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignatureDefElementExtension().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.phaseforward.informadapter.odm.extensions._2.LegalReason }
     * {@link com.phaseforward.informadapter.odm.extensions._2.Meaning }
     * 
     * 
     */
    public List<Object> getSignatureDefElementExtension() {
        if (signatureDefElementExtension == null) {
            signatureDefElementExtension = new ArrayList<Object>();
        }
        return this.signatureDefElementExtension;
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
     * Gets the value of the methodology property.
     * 
     * @return
     *     possible object is
     *     {@link SignMethod }
     *     
     */
    public SignMethod getMethodology() {
        return methodology;
    }

    /**
     * Sets the value of the methodology property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignMethod }
     *     
     */
    public void setMethodology(SignMethod value) {
        this.methodology = value;
    }

    /**
     * Gets the value of the groupName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets the value of the groupName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupName(String value) {
        this.groupName = value;
    }

}
