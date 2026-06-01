package com.departmentstore.api.infrastructure.web.mapper;

import com.departmentstore.api.domain.entity.AuditTrail;
import com.departmentstore.api.infrastructure.web.dto.response.AuditTrailResponseDto;
import org.springframework.stereotype.Component;

@Component
public class AuditTrailMapper {
    public AuditTrailResponseDto toResponseDto(final AuditTrail audit) {

        return new AuditTrailResponseDto(
                audit.getId(),
                audit.getCreatedAt(),
                audit.getUpdatedAt(),
                audit.getDeletedAt(),
                audit.getCreatedBy(),
                audit.getUpdatedBy(),
                audit.getDeletedBy(),
                audit.getDeletedAt() != null ? "EXCLUIDO" : "ATIVO"
        );
    }
}
