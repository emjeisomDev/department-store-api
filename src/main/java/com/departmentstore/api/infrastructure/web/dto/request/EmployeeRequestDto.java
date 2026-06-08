package com.departmentstore.api.infrastructure.web.dto.request;

import com.departmentstore.api.domain.enums.EmployeeRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "Dados para contratação de funcionário")
public record EmployeeRequestDto(

        @Schema(description = "ID da pessoa vinculada ao funcionário", example = "1")
        @NotNull(message = "Person id is required")
        Long personId,

        @Schema(description = "Matrícula do funcionário", example = "EMP-2025001")
        @NotBlank(message = "Registration number is required")
        String registrationNumber,

        @Schema(description = "Data de contratação", example = "2025-01-10")
        @NotNull(message = "Hire date is required")
        LocalDate hireDate,

        @Schema(description = "Cargo do funcionário")
        EmployeeRole employeeRole

) {}