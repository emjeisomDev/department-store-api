package com.departmentstore.api.infrastructure.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Resposta padrão da API")
public record ApiResponseDto<T>(

        @Schema(description = "Indica se a operação foi executada com sucesso", example = "true")
        boolean success,

        @Schema(description = "Dados retornados pela operação")
        T data,

        @Schema(description = "Mensagem informativa da operação", example = "Operation completed successfully")
        String message,

        @Schema(description = "Data e hora da resposta", example = "2025-01-15T10:30:45")
        LocalDateTime timestamp

) {
}