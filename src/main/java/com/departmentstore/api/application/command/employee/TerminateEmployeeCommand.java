package com.departmentstore.api.application.command.employee;

import java.time.LocalDate;

public record TerminateEmployeeCommand(
        Long employeeId,
        LocalDate terminationDate,
        String terminationReason
) {
}
