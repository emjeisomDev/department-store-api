package com.departmentstore.api.infrastructure.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de retorno de cliente")
public record ClientResponseDto(

        @Schema(description = "Identificador do cliente", example = "10")
        Long id,

        @Schema(description = "Nome da pessoa vinculada", example = "João da Silva")
        String personName,

        @Schema(description = "Código do cliente", example = "CLI-000001")
        String clientCode,

        @Schema(description = "Classificação do cliente", example = "VIP")
        String rank,

        @Schema(description = "Status do cliente", example = "ACTIVE")
        String status

) {
}