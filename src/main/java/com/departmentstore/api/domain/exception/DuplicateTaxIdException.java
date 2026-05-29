package com.departmentstore.api.domain.exception;

public class DuplicateTaxIdException extends RuntimeException {
    public DuplicateTaxIdException(final String taxId) {
        super("Tax id already registered: " + taxId);
    }
}
