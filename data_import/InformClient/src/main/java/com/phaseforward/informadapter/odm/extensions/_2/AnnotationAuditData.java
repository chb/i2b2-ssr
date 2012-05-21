
package com.phaseforward.informadapter.odm.extensions._2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.cdisc.ns.odm.v1.Comment;


/**
 * <p>Java class for AnnotationAuditData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AnnotationAuditData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}Comment"/>
 *         &lt;element ref="{http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0}AnnotationAuditRecord"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnnotationAuditData", propOrder = {
    "comment",
    "annotationAuditRecord"
})
public class AnnotationAuditData {

    @XmlElement(name = "Comment", namespace = "http://www.cdisc.org/ns/odm/v1.3", required = true)
    protected Comment comment;
    @XmlElement(name = "AnnotationAuditRecord", required = true)
    protected AnnotationAuditRecord annotationAuditRecord;

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link Comment }
     *     
     */
    public Comment getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comment }
     *     
     */
    public void setComment(Comment value) {
        this.comment = value;
    }

    /**
     * Gets the value of the annotationAuditRecord property.
     * 
     * @return
     *     possible object is
     *     {@link AnnotationAuditRecord }
     *     
     */
    public AnnotationAuditRecord getAnnotationAuditRecord() {
        return annotationAuditRecord;
    }

    /**
     * Sets the value of the annotationAuditRecord property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnnotationAuditRecord }
     *     
     */
    public void setAnnotationAuditRecord(AnnotationAuditRecord value) {
        this.annotationAuditRecord = value;
    }

}
