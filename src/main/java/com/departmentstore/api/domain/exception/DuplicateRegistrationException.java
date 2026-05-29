package com.departmentstore.api.domain.exception;

public class DuplicateRegistrationException extends RuntimeException {
    public DuplicateRegistrationException(final String registrationNumber) {
        super("Registration already exists: " + registrationNumber);
    }
}
