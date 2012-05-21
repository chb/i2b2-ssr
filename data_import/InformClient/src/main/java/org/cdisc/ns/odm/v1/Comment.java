
package org.cdisc.ns.odm.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for Comment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Comment">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.cdisc.org/ns/odm/v1.3>text">
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}CommentAttributeExtension"/>
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.3}CommentAttributeDefinition"/>
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Comment", propOrder = {
    "value"
})
public class Comment {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "SponsorOrSite")
    protected CommentType sponsorOrSite;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the sponsorOrSite property.
     * 
     * @return
     *     possible object is
     *     {@link CommentType }
     *     
     */
    public CommentType getSponsorOrSite() {
        return sponsorOrSite;
    }

    /**
     * Sets the value of the sponsorOrSite property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommentType }
     *     
     */
    public void setSponsorOrSite(CommentType value) {
        this.sponsorOrSite = value;
    }

}
