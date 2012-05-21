
package com.phaseforward.informadapter.odm.extensions._2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParentType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ParentType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ItemDef"/>
 *     &lt;enumeration value="CodeListItem"/>
 *     &lt;enumeration value="NoParent"/>
 *     &lt;maxLength value="12"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ParentType")
@XmlEnum
public enum ParentType {

    @XmlEnumValue("ItemDef")
    ITEM_DEF("ItemDef"),
    @XmlEnumValue("CodeListItem")
    CODE_LIST_ITEM("CodeListItem"),
    @XmlEnumValue("NoParent")
    NO_PARENT("NoParent");
    private final String value;

    ParentType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ParentType fromValue(String v) {
        for (ParentType c: ParentType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
