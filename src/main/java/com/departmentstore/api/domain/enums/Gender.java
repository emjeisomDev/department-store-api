package com.departmentstore.api.domain.enums;

import java.util.Arrays;

public enum Gender {
    MALE("M"),
    FEMALE("F"),
    OTHER("O"),
    NOT_INFORMED("N");

    private final String databaseValue;

    Gender(String databaseValue) {
        this.databaseValue = databaseValue;
    }

    public String getDatabaseValue() {
        return databaseValue;
    }

    public static Gender fromDatabaseValue(String value) {
        return Arrays.stream(values())
                .filter(g -> g.databaseValue.equals(value))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid gender value: " + value));
    }
}
