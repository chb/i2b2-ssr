
package com.phaseforward.informadapter.odm._2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DownloadMetadataODM complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DownloadMetadataODM">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.phaseforward.com/InFormAdapter/ODM/1.0}DownloadMetadataODM">
 *       &lt;attGroup ref="{http://www.phaseforward.com/InFormAdapter/ODM/2.0}ODMv2MetaDataAttributes"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DownloadMetadataODM")
public class DownloadMetadataODM
    extends com.phaseforward.informadapter.odm._1.DownloadMetadataODM
{

    @XmlAttribute
    protected String priorFileOID;
    @XmlAttribute(required = true)
    protected FileType fileType;
    @XmlAttribute(required = true)
    protected String mappingVersion;
    @XmlAttribute
    protected Boolean includeExtensions;
    @XmlAttribute
    protected ODMComplianceMode complianceMode;
    @XmlAttribute
    protected Boolean skipRevisioningWithoutVersioning;
    @XmlAttribute
    protected String studyVersionName;
    @XmlAttribute
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar studyVersionCutoff;

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
     * Gets the value of the includeExtensions property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isIncludeExtensions() {
        if (includeExtensions == null) {
            return true;
        } else {
            return includeExtensions;
        }
    }

    /**
     * Sets the value of the includeExtensions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeExtensions(Boolean value) {
        this.includeExtensions = value;
    }

    /**
     * Gets the value of the complianceMode property.
     * 
     * @return
     *     possible object is
     *     {@link ODMComplianceMode }
     *     
     */
    public ODMComplianceMode getComplianceMode() {
        if (complianceMode == null) {
            return ODMComplianceMode.STRICT;
        } else {
            return complianceMode;
        }
    }

    /**
     * Sets the value of the complianceMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ODMComplianceMode }
     *     
     */
    public void setComplianceMode(ODMComplianceMode value) {
        this.complianceMode = value;
    }

    /**
     * Gets the value of the skipRevisioningWithoutVersioning property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSkipRevisioningWithoutVersioning() {
        return skipRevisioningWithoutVersioning;
    }

    /**
     * Sets the value of the skipRevisioningWithoutVersioning property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSkipRevisioningWithoutVersioning(Boolean value) {
        this.skipRevisioningWithoutVersioning = value;
    }

    /**
     * Gets the value of the studyVersionName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudyVersionName() {
        return studyVersionName;
    }

    /**
     * Sets the value of the studyVersionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudyVersionName(String value) {
        this.studyVersionName = value;
    }

    /**
     * Gets the value of the studyVersionCutoff property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStudyVersionCutoff() {
        return studyVersionCutoff;
    }

    /**
     * Sets the value of the studyVersionCutoff property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStudyVersionCutoff(XMLGregorianCalendar value) {
        this.studyVersionCutoff = value;
    }

}
