package com.departmentstore.api.infrastructure.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Dados de retorno de pessoa física")
public record NaturalPersonResponseDto(

        @Schema(description = "Identificador da pessoa física", example = "1")
        Long id,

        @Schema(description = "Nome completo", example = "João da Silva")
        String name,

        @Schema(description = "CPF formatado", example = "529.982.247-25")
        String cpf,

        @Schema(description = "Data de nascimento", example = "1990-05-20")
        LocalDate birthDate,

        @Schema(description = "Gênero", example = "MALE")
        String gender,

        @Schema(description = "Status do registro", example = "ACTIVE")
        String status,

        @Schema(description = "Data de criação", example = "2025-01-01T10:30:00")
        LocalDateTime createdAt

) {
}
