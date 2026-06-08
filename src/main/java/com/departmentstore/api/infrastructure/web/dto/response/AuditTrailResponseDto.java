package com.departmentstore.api.infrastructure.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Informações de auditoria do registro")
public record AuditTrailResponseDto(

        @Schema(description = "Identificador da auditoria", example = "1")
        Long auditId,

        @Schema(description = "Data de criação", example = "2025-01-01T10:00:00")
        LocalDateTime createdAt,

        @Schema(description = "Data da última atualização", example = "2025-01-10T14:30:00")
        LocalDateTime updatedAt,

        @Schema(description = "Data da exclusão lógica", example = "2025-01-15T08:45:00")
        LocalDateTime deletedAt,

        @Schema(description = "Usuário criador", example = "admin")
        String createdBy,

        @Schema(description = "Usuário que realizou a última atualização", example = "manager")
        String updatedBy,

        @Schema(description = "Usuário responsável pela exclusão", example = "admin")
        String deletedBy,

        @Schema(description = "Status do registro", example = "ACTIVE")
        String recordStatus

) {
}