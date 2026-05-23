package com.departmentstore.api.application.command.employee;

import java.time.LocalDate;

public record HireEmployeeCommand(
        Long personId,
        String registrationNumber,
        LocalDate hireDate,
        String employeeRole
) {
}
