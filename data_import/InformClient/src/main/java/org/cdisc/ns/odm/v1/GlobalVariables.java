
package org.cdisc.ns.odm.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GlobalVariables complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GlobalVariables">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}StudyName"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}StudyDescription"/>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}ProtocolName"/>
 *         &lt;group ref="{http://www.cdisc.org/ns/odm/v1.3}GlobalVariablesElementExtension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}GlobalVariablesAttributeExtension"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GlobalVariables", propOrder = {
    "studyName",
    "studyDescription",
    "protocolName"
})
public class GlobalVariables {

    @XmlElement(name = "StudyName", required = true)
    protected StudyName studyName;
    @XmlElement(name = "StudyDescription", required = true)
    protected StudyDescription studyDescription;
    @XmlElement(name = "ProtocolName", required = true)
    protected ProtocolName protocolName;

    /**
     * Gets the value of the studyName property.
     * 
     * @return
     *     possible object is
     *     {@link StudyName }
     *     
     */
    public StudyName getStudyName() {
        return studyName;
    }

    /**
     * Sets the value of the studyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudyName }
     *     
     */
    public void setStudyName(StudyName value) {
        this.studyName = value;
    }

    /**
     * Gets the value of the studyDescription property.
     * 
     * @return
     *     possible object is
     *     {@link StudyDescription }
     *     
     */
    public StudyDescription getStudyDescription() {
        return studyDescription;
    }

    /**
     * Sets the value of the studyDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudyDescription }
     *     
     */
    public void setStudyDescription(StudyDescription value) {
        this.studyDescription = value;
    }

    /**
     * Gets the value of the protocolName property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolName }
     *     
     */
    public ProtocolName getProtocolName() {
        return protocolName;
    }

    /**
     * Sets the value of the protocolName property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolName }
     *     
     */
    public void setProtocolName(ProtocolName value) {
        this.protocolName = value;
    }

}
