
package com.phaseforward.informadapter.odm.extensions._2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for AnnotationAuditRecord complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AnnotationAuditRecord">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}UserRef" minOccurs="0"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}DateTimeStamp" minOccurs="0"/>
 *         &lt;element ref="{http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0}UserRef" minOccurs="0"/>
 *         &lt;element ref="{http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0}DateTimeStamp" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "AnnotationAuditRecord")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnnotationAuditRecord", propOrder = {
    "content"
})
public class AnnotationAuditRecord {

    @XmlElementRefs({
        @XmlElementRef(name = "DateTimeStamp", namespace = "http://www.cdisc.org/ns/odm/v1.3", type = JAXBElement.class),
        @XmlElementRef(name = "DateTimeStamp", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0", type = JAXBElement.class),
        @XmlElementRef(name = "UserRef", namespace = "http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0", type = JAXBElement.class),
        @XmlElementRef(name = "UserRef", namespace = "http://www.cdisc.org/ns/odm/v1.3", type = JAXBElement.class)
    })
    protected List<JAXBElement<?>> content;

    /**
     * Gets the rest of the content model. 
     * 
     * <p>
     * You are getting this "catch-all" property because of the following reason: 
     * The field name "UserRef" is used by two different parts of a schema. See: 
     * line 77 of file:/home/justin/workplace/carranet-trunk/InformClient/src/jaxws/PhaseForward_ODM.xsd
     * line 75 of file:/home/justin/workplace/carranet-trunk/InformClient/src/jaxws/PhaseForward_ODM.xsd
     * <p>
     * To get rid of this property, apply a property customization to one 
     * of both of the following declarations to change their names: 
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link com.phaseforward.informadapter.odm.extensions._2.UserRef }{@code >}
     * {@link JAXBElement }{@code <}{@link org.cdisc.ns.odm.v1.DateTimeStamp }{@code >}
     * {@link JAXBElement }{@code <}{@link org.cdisc.ns.odm.v1.UserRef }{@code >}
     * {@link JAXBElement }{@code <}{@link com.phaseforward.informadapter.odm.extensions._2.DateTimeStamp }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getContent() {
        if (content == null) {
            content = new ArrayList<JAXBElement<?>>();
        }
        return this.content;
    }

}
