package com.departmentstore.api.domain.exception;

public class ResponsibleRequiredException extends RuntimeException {
    public ResponsibleRequiredException() {
        super("Legal person must have at least one responsible");
    }
}
