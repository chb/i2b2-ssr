
package com.phaseforward.informadapter.odm.extensions._2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemDefType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ItemDefType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="RadioGroup"/>
 *     &lt;enumeration value="CheckBox"/>
 *     &lt;enumeration value="Text"/>
 *     &lt;enumeration value="Calculated"/>
 *     &lt;enumeration value="PullDown"/>
 *     &lt;enumeration value="DateTime"/>
 *     &lt;enumeration value="UnknownItemDefType"/>
 *     &lt;maxLength value="18"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ItemDefType")
@XmlEnum
public enum ItemDefType {

    @XmlEnumValue("RadioGroup")
    RADIO_GROUP("RadioGroup"),
    @XmlEnumValue("CheckBox")
    CHECK_BOX("CheckBox"),
    @XmlEnumValue("Text")
    TEXT("Text"),
    @XmlEnumValue("Calculated")
    CALCULATED("Calculated"),
    @XmlEnumValue("PullDown")
    PULL_DOWN("PullDown"),
    @XmlEnumValue("DateTime")
    DATE_TIME("DateTime"),
    @XmlEnumValue("UnknownItemDefType")
    UNKNOWN_ITEM_DEF_TYPE("UnknownItemDefType");
    private final String value;

    ItemDefType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ItemDefType fromValue(String v) {
        for (ItemDefType c: ItemDefType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
