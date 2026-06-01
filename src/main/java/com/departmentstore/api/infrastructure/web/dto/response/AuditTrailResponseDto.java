package com.departmentstore.api.infrastructure.web.dto.response;

import java.time.LocalDateTime;

public record AuditTrailResponseDto(
        Long auditId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        String createdBy,
        String updatedBy,
        String deletedBy,
        String recordStatus
) {
}
