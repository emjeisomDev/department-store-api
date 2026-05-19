package com.departmentstore.api.domain.repository;

import com.departmentstore.api.domain.entity.AuditTrail;

import java.util.Optional;

public interface AuditTrailRepository {
    AuditTrail save(AuditTrail auditTrail);
    Optional<AuditTrail> findById(Long id);
    Optional<AuditTrail> findByRecordIdAndTableName(Long recordId, String tableName);
}
