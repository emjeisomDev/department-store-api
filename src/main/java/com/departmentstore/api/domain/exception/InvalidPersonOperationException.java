package com.departmentstore.api.domain.exception;

public class InvalidPersonOperationException extends RuntimeException {
    public InvalidPersonOperationException(String message) {
        super(message);
    }
}
