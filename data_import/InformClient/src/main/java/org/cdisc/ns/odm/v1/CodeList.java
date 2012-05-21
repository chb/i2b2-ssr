
package org.cdisc.ns.odm.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CodeList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CodeList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}CodeListItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}ExternalCodeList" minOccurs="0"/>
 *         &lt;group ref="{http://www.cdisc.org/ns/odm/v1.3}CodeListElementExtension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}CodeListAttributeExtension"/>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}CodeListAttributeDefinition"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CodeList", propOrder = {
    "codeListItem",
    "externalCodeList"
})
public class CodeList {

    @XmlElement(name = "CodeListItem")
    protected List<CodeListItem> codeListItem;
    @XmlElement(name = "ExternalCodeList")
    protected ExternalCodeList externalCodeList;
    @XmlAttribute(name = "OID", required = true)
    protected String oid;
    @XmlAttribute(name = "Name", required = true)
    protected String name;
    @XmlAttribute(name = "DataType", required = true)
    protected CLDataType dataType;
    @XmlAttribute(name = "SASFormatName")
    protected String sasFormatName;

    /**
     * Gets the value of the codeListItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the codeListItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCodeListItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CodeListItem }
     * 
     * 
     */
    public List<CodeListItem> getCodeListItem() {
        if (codeListItem == null) {
            codeListItem = new ArrayList<CodeListItem>();
        }
        return this.codeListItem;
    }

    /**
     * Gets the value of the externalCodeList property.
     * 
     * @return
     *     possible object is
     *     {@link ExternalCodeList }
     *     
     */
    public ExternalCodeList getExternalCodeList() {
        return externalCodeList;
    }

    /**
     * Sets the value of the externalCodeList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExternalCodeList }
     *     
     */
    public void setExternalCodeList(ExternalCodeList value) {
        this.externalCodeList = value;
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
     *     {@link CLDataType }
     *     
     */
    public CLDataType getDataType() {
        return dataType;
    }

    /**
     * Sets the value of the dataType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CLDataType }
     *     
     */
    public void setDataType(CLDataType value) {
        this.dataType = value;
    }

    /**
     * Gets the value of the sasFormatName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSASFormatName() {
        return sasFormatName;
    }

    /**
     * Sets the value of the sasFormatName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSASFormatName(String value) {
        this.sasFormatName = value;
    }

}
