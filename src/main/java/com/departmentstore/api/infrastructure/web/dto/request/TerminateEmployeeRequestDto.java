package com.departmentstore.api.infrastructure.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Dados para desligamento de funcionário")
public record TerminateEmployeeRequestDto(

        @Schema(description = "Data de desligamento", example = "2025-12-31")
        @NotNull(message = "Termination date is required")
        LocalDate terminationDate,

        @Schema(description = "Motivo do desligamento", example = "Pedido de demissão")
        String terminationReason

) {}