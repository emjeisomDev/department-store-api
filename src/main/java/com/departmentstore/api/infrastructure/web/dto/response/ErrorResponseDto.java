package com.departmentstore.api.infrastructure.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Schema(description = "Resposta padrão para erros")
public record ErrorResponseDto(

        @Schema(description = "Código interno do erro", example = "PERSON_NOT_FOUND")
        String code,

        @Schema(description = "Mensagem do erro", example = "Person not found")
        String message,

        @Schema(description = "Detalhes adicionais do erro")
        Map<String, Object> details,

        @Schema(description = "Data e hora do erro", example = "2025-01-15T10:30:45")
        LocalDateTime timestamp

) {
}
