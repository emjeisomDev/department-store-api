package com.departmentstore.api.domain.exception;

public class ResponsibleNotFoundException extends RuntimeException {
    public ResponsibleNotFoundException(final Long responsibilityId) {
        super("Responsible not found. Id=" + responsibilityId);
    }

    public ResponsibleNotFoundException(final String message) {
        super(message);
    }
}
