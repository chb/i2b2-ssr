
package org.cdisc.ns.odm.v1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SoftOrHard.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SoftOrHard">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Soft"/>
 *     &lt;enumeration value="Hard"/>
 *     &lt;maxLength value="4"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SoftOrHard")
@XmlEnum
public enum SoftOrHard {

    @XmlEnumValue("Soft")
    SOFT("Soft"),
    @XmlEnumValue("Hard")
    HARD("Hard");
    private final String value;

    SoftOrHard(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SoftOrHard fromValue(String v) {
        for (SoftOrHard c: SoftOrHard.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
