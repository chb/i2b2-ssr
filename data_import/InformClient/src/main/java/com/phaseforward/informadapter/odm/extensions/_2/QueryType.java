
package com.phaseforward.informadapter.odm.extensions._2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QueryType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="QueryType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="User"/>
 *     &lt;enumeration value="Autoquery"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "QueryType")
@XmlEnum
public enum QueryType {

    @XmlEnumValue("User")
    USER("User"),
    @XmlEnumValue("Autoquery")
    AUTOQUERY("Autoquery");
    private final String value;

    QueryType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static QueryType fromValue(String v) {
        for (QueryType c: QueryType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
