package edu.chip.carranet.auth.backend;


public enum AuthCapabilityEnum {

    CHANGE_PASSWORD("changePassword");

    private String stringValue;

    private AuthCapabilityEnum(String value){
        this.stringValue = value;
    }

    public String getStringValue() {
        return stringValue;
    }
}
