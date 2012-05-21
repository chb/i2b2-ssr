
package com.phaseforward.informadapter.odm.extensions._2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.cdisc.ns.odm.v1.AuditRecord;


/**
 * <p>Java class for QueryStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QueryStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}AuditRecord"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Status" use="required" type="{http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0}QueryState" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryStatus", propOrder = {
    "auditRecord"
})
public class QueryStatus {

    @XmlElement(name = "AuditRecord", namespace = "http://www.cdisc.org/ns/odm/v1.3", required = true)
    protected AuditRecord auditRecord;
    @XmlAttribute(name = "Status", required = true)
    protected QueryState status;

    /**
     * Gets the value of the auditRecord property.
     * 
     * @return
     *     possible object is
     *     {@link AuditRecord }
     *     
     */
    public AuditRecord getAuditRecord() {
        return auditRecord;
    }

    /**
     * Sets the value of the auditRecord property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuditRecord }
     *     
     */
    public void setAuditRecord(AuditRecord value) {
        this.auditRecord = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link QueryState }
     *     
     */
    public QueryState getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryState }
     *     
     */
    public void setStatus(QueryState value) {
        this.status = value;
    }

}
