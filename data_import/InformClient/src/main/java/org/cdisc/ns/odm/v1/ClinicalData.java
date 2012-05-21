
package org.cdisc.ns.odm.v1;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ClinicalData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClinicalData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}SubjectData" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;group ref="{http://www.cdisc.org/ns/odm/v1.3}ClinicalDataElementExtension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}ClinicalDataAttributeExtension"/>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}ClinicalDataAttributeDefinition"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name="ClinicalData")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClinicalData", propOrder = {
    "subjectData"
})
public class ClinicalData {

    @XmlElement(name = "SubjectData")
    protected List<SubjectData> subjectData;
    @XmlAttribute(name = "TransactionGuid", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected String transactionGuid;
    @XmlAttribute(name = "Revision", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected BigDecimal revision;
    @XmlAttribute(name = "StudyOID", required = true)
    protected String studyOID;
    @XmlAttribute(name = "MetaDataVersionOID", required = true)
    protected String metaDataVersionOID;

    /**
     * Gets the value of the subjectData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subjectData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubjectData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubjectData }
     * 
     * 
     */
    public List<SubjectData> getSubjectData() {
        if (subjectData == null) {
            subjectData = new ArrayList<SubjectData>();
        }
        return this.subjectData;
    }

    /**
     * Gets the value of the transactionGuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionGuid() {
        return transactionGuid;
    }

    /**
     * Sets the value of the transactionGuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionGuid(String value) {
        this.transactionGuid = value;
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
     * Gets the value of the studyOID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudyOID() {
        return studyOID;
    }

    /**
     * Sets the value of the studyOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudyOID(String value) {
        this.studyOID = value;
    }

    /**
     * Gets the value of the metaDataVersionOID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetaDataVersionOID() {
        return metaDataVersionOID;
    }

    /**
     * Sets the value of the metaDataVersionOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetaDataVersionOID(String value) {
        this.metaDataVersionOID = value;
    }

}
