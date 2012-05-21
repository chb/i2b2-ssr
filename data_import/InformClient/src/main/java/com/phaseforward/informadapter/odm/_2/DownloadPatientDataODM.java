
package com.phaseforward.informadapter.odm._2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DownloadPatientDataODM complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DownloadPatientDataODM">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.phaseforward.com/InFormAdapter/ODM/1.0}DownloadPatientDataODM">
 *       &lt;attGroup ref="{http://www.phaseforward.com/InFormAdapter/ODM/2.0}ODMv2Attributes"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DownloadPatientDataODM")
public class DownloadPatientDataODM
    extends com.phaseforward.informadapter.odm._1.DownloadPatientDataODM
{

    @XmlAttribute
    protected String priorFileOID;
    @XmlAttribute(required = true)
    protected String mappingVersion;
    @XmlAttribute
    protected Boolean includeExtensions;
    @XmlAttribute
    protected ODMComplianceMode complianceMode;

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

}
