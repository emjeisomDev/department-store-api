package com.departmentstore.api.domain.exception;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(final Long personId) {
        super("Person not found. Id=" + personId);
    }

    public PersonNotFoundException(final String message) {
        super(message);
    }

}
