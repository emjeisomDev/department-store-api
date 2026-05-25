package com.departmentstore.api.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TerminateEmployeeRequestDto(
        @NotNull(message = "Termination date is required")
        LocalDate terminationDate,
        String terminationReason
) {
}
