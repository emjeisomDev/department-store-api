package com.departmentstore.api.domain.exception;

public class EmployeeMustBeNaturalPersonException extends RuntimeException {
    public EmployeeMustBeNaturalPersonException() {
        super("Only natural persons can be employees");
    }
}
