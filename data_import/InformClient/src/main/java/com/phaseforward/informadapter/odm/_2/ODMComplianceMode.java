
package com.phaseforward.informadapter.odm._2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ODMComplianceMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ODMComplianceMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Strict"/>
 *     &lt;enumeration value="Loose"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ODMComplianceMode")
@XmlEnum
public enum ODMComplianceMode {

    @XmlEnumValue("Strict")
    STRICT("Strict"),
    @XmlEnumValue("Loose")
    LOOSE("Loose");
    private final String value;

    ODMComplianceMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ODMComplianceMode fromValue(String v) {
        for (ODMComplianceMode c: ODMComplianceMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
