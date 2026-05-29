package com.departmentstore.api.domain.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(final Long employeeId ) {
        super("Employee not found. Id=" + employeeId);
    }

    public EmployeeNotFoundException(final String message) {
        super(message);
    }
}
