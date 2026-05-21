package com.departmentstore.api.infrastructure.persistence.repository;

import com.departmentstore.api.infrastructure.persistence.entity.AuditTrailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuditTrailJpaRepository extends JpaRepository<AuditTrailEntity, Long> {
    Optional<AuditTrailEntity> findByRecordIdAndTableName(Long recordId, String tableName);
}
