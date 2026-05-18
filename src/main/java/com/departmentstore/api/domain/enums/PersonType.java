package com.departmentstore.api.domain.enums;

public enum PersonType {
    NATURAL_PERSON("F"),
    LEGAL_PERSON("J");

    private final String code;

    PersonType(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static PersonType fromCode(final String code) {
        for(PersonType value : values()) {
            if(value.code.equalsIgnoreCase(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid PersonType code: " + code);
    }


}
