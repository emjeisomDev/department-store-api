package com.departmentstore.api.infrastructure.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Dados de retorno de funcionário")
public record EmployeeResponseDto(

        @Schema(description = "Identificador do funcionário", example = "20")
        Long id,

        @Schema(description = "Nome da pessoa vinculada", example = "Maria Oliveira")
        String personName,

        @Schema(description = "Matrícula do funcionário", example = "EMP-2025001")
        String registrationNumber,

        @Schema(description = "Data de contratação", example = "2025-01-01")
        LocalDate hireDate,

        @Schema(description = "Status do funcionário", example = "ACTIVE")
        String status,

        @Schema(description = "Cargo do funcionário", example = "MANAGER")
        String role

) {
}
