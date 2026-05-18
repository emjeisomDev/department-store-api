package com.departmentstore.api.domain.entity;

import com.departmentstore.api.domain.enums.EmployeeRole;
import com.departmentstore.api.domain.enums.EmployeeStatus;

import java.time.LocalDate;
import java.util.Objects;

public class Employee {

    private final Long id;
    private final Long personId;
    private final String registrationNumber;
    private final LocalDate hireDate;
    private LocalDate terminationDate;
    private String terminationReason;
    private EmployeeRole employeeRole;
    private EmployeeStatus status;

    public Employee(
            final Long id,
            final Long personId,
            final String registrationNumber,
            final LocalDate hireDate,
            final EmployeeRole employeeRole,
            final EmployeeStatus status
    ) {
        validate(personId, registrationNumber, hireDate, employeeRole, status);
        this.id = id;
        this.personId = personId;
        this.registrationNumber = registrationNumber;
        this.hireDate = hireDate;
        this.employeeRole = employeeRole;
        this.status = status;
    }

    private void validate(
            final Long personId,
            final String registrationNumber,
            final LocalDate hireDate,
            final EmployeeRole employeeRole,
            final EmployeeStatus status
    ) {

        if (personId == null) {
            throw new IllegalArgumentException("PersonId is required");
        }

        if (registrationNumber == null || registrationNumber.isBlank()) {
            throw new IllegalArgumentException("Registration number is required");
        }

        if (hireDate == null) {
            throw new IllegalArgumentException("Hire date is required");
        }

        if (employeeRole == null) {
            throw new IllegalArgumentException("Employee role is required");
        }

        if (status == null) {
            throw new IllegalArgumentException("Employee status is required");
        }
    }

    public void terminate(final LocalDate terminationDate, final String terminationReason) {

        if (terminationDate == null) {
            throw new IllegalArgumentException("Termination date is required");
        }

        if (terminationDate.isBefore(hireDate)) {
            throw new IllegalArgumentException("Termination date cannot be before hire date");
        }

        if (terminationReason == null || terminationReason.isBlank()) {
            throw new IllegalArgumentException("Termination reason is required");
        }

        this.terminationDate = terminationDate;
        this.terminationReason = terminationReason;
        this.status = EmployeeStatus.INACTIVE;
    }

    public void changeRole(final EmployeeRole employeeRole) {
        if (employeeRole == null) {
            throw new IllegalArgumentException("Employee role is required");
        }
        this.employeeRole = employeeRole;
    }

    public Long getId() {
        return id;
    }

    public Long getPersonId() {
        return personId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public String getTerminationReason() {
        return terminationReason;
    }

    public EmployeeRole getEmployeeRole() {
        return employeeRole;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Employee employee)) {
            return false;
        }

        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
