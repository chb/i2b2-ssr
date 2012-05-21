
package com.phaseforward.informadapter.odm.extensions._2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.cdisc.ns.odm.v1.TranslatedText;


/**
 * <p>Java class for Meaning complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Meaning">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.3}TranslatedText" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0}DO_NOT_USE_ForceCodeGen" maxOccurs="0" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Meaning", propOrder = {
    "translatedText"
})
public class Meaning {

    @XmlElement(name = "TranslatedText", namespace = "http://www.cdisc.org/ns/odm/v1.3")
    protected List<TranslatedText> translatedText;

    /**
     * Gets the value of the translatedText property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the translatedText property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTranslatedText().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TranslatedText }
     * 
     * 
     */
    public List<TranslatedText> getTranslatedText() {
        if (translatedText == null) {
            translatedText = new ArrayList<TranslatedText>();
        }
        return this.translatedText;
    }

}
