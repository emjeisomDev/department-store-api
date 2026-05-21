package com.departmentstore.api.infrastructure.persistence.repository.adapter;

import com.departmentstore.api.domain.entity.AuditTrail;
import com.departmentstore.api.domain.repository.AuditTrailRepository;
import com.departmentstore.api.infrastructure.persistence.entity.AuditTrailEntity;
import com.departmentstore.api.infrastructure.persistence.repository.AuditTrailJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditTrailRepositoryAdapter
        implements AuditTrailRepository {

    private final AuditTrailJpaRepository repository;

    public AuditTrailRepositoryAdapter(final AuditTrailJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public AuditTrail save(final AuditTrail auditTrail) {
        AuditTrailEntity entity = toEntity(auditTrail);
        AuditTrailEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<AuditTrail> findById(final Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<AuditTrail>
    findByRecordIdAndTableName(final Long recordId, final String tableName) {
        return repository
                .findByRecordIdAndTableName(recordId, tableName)
                .map(this::toDomain);
    }

    private AuditTrail toDomain(final AuditTrailEntity entity) {
        return new AuditTrail(
                entity.getId(),
                entity.getTableName(),
                entity.getRecordId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedBy(),
                entity.getDeletedBy()
        );
    }

    private AuditTrailEntity toEntity(final AuditTrail domain) {
        AuditTrailEntity entity = new AuditTrailEntity();
        entity.setTableName(domain.getTableName());
        entity.setRecordId(domain.getRecordId());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setDeletedAt(domain.getDeletedAt());
        entity.setCreatedBy(domain.getCreatedBy());
        entity.setUpdatedBy(domain.getUpdatedBy());
        entity.setDeletedBy(domain.getDeletedBy());
        return entity;
    }
}
