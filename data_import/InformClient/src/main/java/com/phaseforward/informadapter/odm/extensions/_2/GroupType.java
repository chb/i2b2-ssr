
package com.phaseforward.informadapter.odm.extensions._2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GroupType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GroupType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Site"/>
 *     &lt;enumeration value="Query"/>
 *     &lt;enumeration value="Rights"/>
 *     &lt;enumeration value="Signature"/>
 *     &lt;enumeration value="Manager_User"/>
 *     &lt;enumeration value="ItemGroup"/>
 *     &lt;enumeration value="ReportingGroup"/>
 *     &lt;enumeration value="UnknownGroupType"/>
 *     &lt;maxLength value="16"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GroupType")
@XmlEnum
public enum GroupType {

    @XmlEnumValue("Site")
    SITE("Site"),
    @XmlEnumValue("Query")
    QUERY("Query"),
    @XmlEnumValue("Rights")
    RIGHTS("Rights"),
    @XmlEnumValue("Signature")
    SIGNATURE("Signature"),
    @XmlEnumValue("Manager_User")
    MANAGER_USER("Manager_User"),
    @XmlEnumValue("ItemGroup")
    ITEM_GROUP("ItemGroup"),
    @XmlEnumValue("ReportingGroup")
    REPORTING_GROUP("ReportingGroup"),
    @XmlEnumValue("UnknownGroupType")
    UNKNOWN_GROUP_TYPE("UnknownGroupType");
    private final String value;

    GroupType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GroupType fromValue(String v) {
        for (GroupType c: GroupType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
