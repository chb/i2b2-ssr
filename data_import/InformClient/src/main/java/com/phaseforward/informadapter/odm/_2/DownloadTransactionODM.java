
package com.phaseforward.informadapter.odm._2;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for DownloadTransactionODM complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DownloadTransactionODM">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.phaseforward.com/InFormAdapter/ODM/1.0}DownloadTransactionODM">
 *       &lt;attGroup ref="{http://www.phaseforward.com/InFormAdapter/ODM/2.0}ODMv2Attributes"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "DownloadTransactionODM")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DownloadTransactionODM")
public class DownloadTransactionODM
    extends com.phaseforward.informadapter.odm._1.DownloadTransactionODM
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
