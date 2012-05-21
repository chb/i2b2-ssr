
package com.phaseforward.informadapter.odm.extensions._2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SubjectState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SubjectState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Screened"/>
 *     &lt;enumeration value="ScreenFailed"/>
 *     &lt;enumeration value="Enrolled"/>
 *     &lt;enumeration value="EnrollmentFailed"/>
 *     &lt;enumeration value="EnrollmentOverride"/>
 *     &lt;enumeration value="Randomized"/>
 *     &lt;enumeration value="DroppedOut"/>
 *     &lt;enumeration value="Complete"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SubjectState")
@XmlEnum
public enum SubjectState {

    @XmlEnumValue("Screened")
    SCREENED("Screened"),
    @XmlEnumValue("ScreenFailed")
    SCREEN_FAILED("ScreenFailed"),
    @XmlEnumValue("Enrolled")
    ENROLLED("Enrolled"),
    @XmlEnumValue("EnrollmentFailed")
    ENROLLMENT_FAILED("EnrollmentFailed"),
    @XmlEnumValue("EnrollmentOverride")
    ENROLLMENT_OVERRIDE("EnrollmentOverride"),
    @XmlEnumValue("Randomized")
    RANDOMIZED("Randomized"),
    @XmlEnumValue("DroppedOut")
    DROPPED_OUT("DroppedOut"),
    @XmlEnumValue("Complete")
    COMPLETE("Complete");
    private final String value;

    SubjectState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SubjectState fromValue(String v) {
        for (SubjectState c: SubjectState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
