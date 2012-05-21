
package com.phaseforward.informadapter.odm.extensions._2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QueryState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="QueryState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Open"/>
 *     &lt;enumeration value="Answered"/>
 *     &lt;enumeration value="Closed"/>
 *     &lt;enumeration value="Candidate"/>
 *     &lt;enumeration value="Deleted"/>
 *     &lt;enumeration value="Sponsor Conflict"/>
 *     &lt;enumeration value="Site Conflict"/>
 *     &lt;enumeration value="Reissued"/>
 *     &lt;maxLength value="15"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "QueryState")
@XmlEnum
public enum QueryState {

    @XmlEnumValue("Open")
    OPEN("Open"),
    @XmlEnumValue("Answered")
    ANSWERED("Answered"),
    @XmlEnumValue("Closed")
    CLOSED("Closed"),
    @XmlEnumValue("Candidate")
    CANDIDATE("Candidate"),
    @XmlEnumValue("Deleted")
    DELETED("Deleted"),
    @XmlEnumValue("Sponsor Conflict")
    SPONSOR_CONFLICT("Sponsor Conflict"),
    @XmlEnumValue("Site Conflict")
    SITE_CONFLICT("Site Conflict"),
    @XmlEnumValue("Reissued")
    REISSUED("Reissued");
    private final String value;

    QueryState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static QueryState fromValue(String v) {
        for (QueryState c: QueryState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
