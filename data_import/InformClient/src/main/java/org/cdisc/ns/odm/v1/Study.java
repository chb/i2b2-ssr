
package org.cdisc.ns.odm.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Study complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Study">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}GlobalVariables"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}BasicDefinitions" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}MetaDataVersion" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;group ref="{http://www.cdisc.org/ns/odm/v1.3}StudyElementExtension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}StudyAttributeDefinition"/>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}StudyAttributeExtension"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Study", propOrder = {
    "globalVariables",
    "basicDefinitions",
    "metaDataVersion"
})
public class Study {

    @XmlElement(name = "GlobalVariables", required = true)
    protected GlobalVariables globalVariables;
    @XmlElement(name = "BasicDefinitions")
    protected BasicDefinitions basicDefinitions;
    @XmlElement(name = "MetaDataVersion")
    protected List<MetaDataVersion> metaDataVersion;
    @XmlAttribute(name = "OID", required = true)
    protected String oid;

    /**
     * Gets the value of the globalVariables property.
     * 
     * @return
     *     possible object is
     *     {@link GlobalVariables }
     *     
     */
    public GlobalVariables getGlobalVariables() {
        return globalVariables;
    }

    /**
     * Sets the value of the globalVariables property.
     * 
     * @param value
     *     allowed object is
     *     {@link GlobalVariables }
     *     
     */
    public void setGlobalVariables(GlobalVariables value) {
        this.globalVariables = value;
    }

    /**
     * Gets the value of the basicDefinitions property.
     * 
     * @return
     *     possible object is
     *     {@link BasicDefinitions }
     *     
     */
    public BasicDefinitions getBasicDefinitions() {
        return basicDefinitions;
    }

    /**
     * Sets the value of the basicDefinitions property.
     * 
     * @param value
     *     allowed object is
     *     {@link BasicDefinitions }
     *     
     */
    public void setBasicDefinitions(BasicDefinitions value) {
        this.basicDefinitions = value;
    }

    /**
     * Gets the value of the metaDataVersion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the metaDataVersion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMetaDataVersion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MetaDataVersion }
     * 
     * 
     */
    public List<MetaDataVersion> getMetaDataVersion() {
        if (metaDataVersion == null) {
            metaDataVersion = new ArrayList<MetaDataVersion>();
        }
        return this.metaDataVersion;
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

}
