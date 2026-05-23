package com.departmentstore.api.infrastructure.web.dto.request;

import com.departmentstore.api.domain.enums.EmployeeRole;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record EmployeeRequestDto(
        @NotNull(message = "Person id is required")
        Long personId,

        @NotBlank(message = "Registration number is required")
        String registrationNumber,

        @NotNull(message = "Hire date is required")
        LocalDate hireDate,

        EmployeeRole employeeRole
) {
}
