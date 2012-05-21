
package org.cdisc.ns.odm.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}Study" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}AdminData" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}ReferenceData" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}ClinicalData" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}Association" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;group ref="{http://www.cdisc.org/ns/odm/v1.3}ODMElementExtension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}ODMAttributeDefinition"/>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}ODMAttributeExtension"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "study",
    "adminData",
    "referenceData",
    "clinicalData",
    "association"
})
@XmlRootElement(name = "ODM")
public class ODM {

    @XmlElement(name = "Study")
    protected List<Study> study;
    @XmlElement(name = "AdminData")
    protected List<AdminData> adminData;
    @XmlElement(name = "ReferenceData")
    protected List<ReferenceData> referenceData;
    @XmlElement(name = "ClinicalData")
    protected List<ClinicalData> clinicalData;
    @XmlElement(name = "Association")
    protected List<Association> association;
    @XmlAttribute(name = "Description")
    protected String description;
    @XmlAttribute(name = "FileType", required = true)
    protected FileType fileType;
    @XmlAttribute(name = "Granularity")
    protected Granularity granularity;
    @XmlAttribute(name = "Archival")
    protected YesOnly archival;
    @XmlAttribute(name = "FileOID", required = true)
    protected String fileOID;
    @XmlAttribute(name = "CreationDateTime", required = true)
    protected String creationDateTime;
    @XmlAttribute(name = "PriorFileOID")
    protected String priorFileOID;
    @XmlAttribute(name = "AsOfDateTime")
    protected String asOfDateTime;
    @XmlAttribute(name = "ODMVersion")
    protected String odmVersion;
    @XmlAttribute(name = "Originator")
    protected String originator;
    @XmlAttribute(name = "SourceSystem")
    protected String sourceSystem;
    @XmlAttribute(name = "SourceSystemVersion")
    protected String sourceSystemVersion;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "MappingVersion", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected String mappingVersion;
    @XmlAttribute(name = "HierarchicalOIDs", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected String hierarchicalOIDs;
    @XmlAttribute(name = "InFormAdapterVersion", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0")
    protected String inFormAdapterVersion;

    /**
     * Gets the value of the study property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the study property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStudy().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Study }
     * 
     * 
     */
    public List<Study> getStudy() {
        if (study == null) {
            study = new ArrayList<Study>();
        }
        return this.study;
    }

    /**
     * Gets the value of the adminData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the adminData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdminData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdminData }
     * 
     * 
     */
    public List<AdminData> getAdminData() {
        if (adminData == null) {
            adminData = new ArrayList<AdminData>();
        }
        return this.adminData;
    }

    /**
     * Gets the value of the referenceData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referenceData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferenceData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReferenceData }
     * 
     * 
     */
    public List<ReferenceData> getReferenceData() {
        if (referenceData == null) {
            referenceData = new ArrayList<ReferenceData>();
        }
        return this.referenceData;
    }

    /**
     * Gets the value of the clinicalData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clinicalData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClinicalData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClinicalData }
     * 
     * 
     */
    public List<ClinicalData> getClinicalData() {
        if (clinicalData == null) {
            clinicalData = new ArrayList<ClinicalData>();
        }
        return this.clinicalData;
    }

    /**
     * Gets the value of the association property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the association property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssociation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Association }
     * 
     * 
     */
    public List<Association> getAssociation() {
        if (association == null) {
            association = new ArrayList<Association>();
        }
        return this.association;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the fileType property.
     * 
     * @return
     *     possible object is
     *     {@link FileType }
     *     
     */
    public FileType getFileType() {
        return fileType;
    }

    /**
     * Sets the value of the fileType property.
     * 
     * @param value
     *     allowed object is
     *     {@link FileType }
     *     
     */
    public void setFileType(FileType value) {
        this.fileType = value;
    }

    /**
     * Gets the value of the granularity property.
     * 
     * @return
     *     possible object is
     *     {@link Granularity }
     *     
     */
    public Granularity getGranularity() {
        return granularity;
    }

    /**
     * Sets the value of the granularity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Granularity }
     *     
     */
    public void setGranularity(Granularity value) {
        this.granularity = value;
    }

    /**
     * Gets the value of the archival property.
     * 
     * @return
     *     possible object is
     *     {@link YesOnly }
     *     
     */
    public YesOnly getArchival() {
        return archival;
    }

    /**
     * Sets the value of the archival property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOnly }
     *     
     */
    public void setArchival(YesOnly value) {
        this.archival = value;
    }

    /**
     * Gets the value of the fileOID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileOID() {
        return fileOID;
    }

    /**
     * Sets the value of the fileOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileOID(String value) {
        this.fileOID = value;
    }

    /**
     * Gets the value of the creationDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreationDateTime() {
        return creationDateTime;
    }

    /**
     * Sets the value of the creationDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreationDateTime(String value) {
        this.creationDateTime = value;
    }

    /**
     * Gets the value of the priorFileOID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriorFileOID() {
        return priorFileOID;
    }

    /**
     * Sets the value of the priorFileOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriorFileOID(String value) {
        this.priorFileOID = value;
    }

    /**
     * Gets the value of the asOfDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsOfDateTime() {
        return asOfDateTime;
    }

    /**
     * Sets the value of the asOfDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsOfDateTime(String value) {
        this.asOfDateTime = value;
    }

    /**
     * Gets the value of the odmVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getODMVersion() {
        return odmVersion;
    }

    /**
     * Sets the value of the odmVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setODMVersion(String value) {
        this.odmVersion = value;
    }

    /**
     * Gets the value of the originator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginator() {
        return originator;
    }

    /**
     * Sets the value of the originator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginator(String value) {
        this.originator = value;
    }

    /**
     * Gets the value of the sourceSystem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceSystem() {
        return sourceSystem;
    }

    /**
     * Sets the value of the sourceSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceSystem(String value) {
        this.sourceSystem = value;
    }

    /**
     * Gets the value of the sourceSystemVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceSystemVersion() {
        return sourceSystemVersion;
    }

    /**
     * Sets the value of the sourceSystemVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceSystemVersion(String value) {
        this.sourceSystemVersion = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the mappingVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMappingVersion() {
        return mappingVersion;
    }

    /**
     * Sets the value of the mappingVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMappingVersion(String value) {
        this.mappingVersion = value;
    }

    /**
     * Gets the value of the hierarchicalOIDs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHierarchicalOIDs() {
        return hierarchicalOIDs;
    }

    /**
     * Sets the value of the hierarchicalOIDs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHierarchicalOIDs(String value) {
        this.hierarchicalOIDs = value;
    }

    /**
     * Gets the value of the inFormAdapterVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInFormAdapterVersion() {
        return inFormAdapterVersion;
    }

    /**
     * Sets the value of the inFormAdapterVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInFormAdapterVersion(String value) {
        this.inFormAdapterVersion = value;
    }

}
