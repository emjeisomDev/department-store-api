package com.departmentstore.api.domain.exception;

public class PersonTypeConflictException extends RuntimeException {
    public PersonTypeConflictException(final String message) {
        super(message);
    }
}
