package com.departmentstore.api.infrastructure.web.dto.response;

import java.time.LocalDate;

public record EmployeeResponseDto(
        Long id,
        String personName,
        String registrationNumber,
        LocalDate hireDate,
        String status,
        String role
) {
}
