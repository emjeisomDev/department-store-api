package com.departmentstore.api.domain.exception;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(final Long clientId) {
        super("Client not found. Id=" + clientId);
    }

    public ClientNotFoundException(final String message) {
        super(message);
    }
}
