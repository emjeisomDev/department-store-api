package com.departmentstore.api.domain.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class AuditTrail {
    private final Long id;
    private final String tableName;
    private final Long recordId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;
    private final String createdBy;
    private final String updatedBy;
    private final String deletedBy;

    public AuditTrail(
            final Long id,
            final String tableName,
            final Long recordId,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt,
            final LocalDateTime deletedAt,
            final String createdBy,
            final String updatedBy,
            final String deletedBy
    ) {

        validate(tableName, recordId, createdAt);

        this.id = id;
        this.tableName = tableName;
        this.recordId = recordId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.deletedBy = deletedBy;
    }

    private void validate(final String tableName, final Long recordId, final LocalDateTime createdAt
    ) {

        if (tableName == null || tableName.isBlank()) {
            throw new IllegalArgumentException("Table name is required");
        }

        if (recordId == null) {
            throw new IllegalArgumentException("RecordId is required");
        }

        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt is required");
        }
    }

    public boolean isDeleted() {
        return deletedAt != null;
    }

    public Long getId() {
        return id;
    }

    public String getTableName() {
        return tableName;
    }

    public Long getRecordId() {
        return recordId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof AuditTrail that)) {
            return false;
        }

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
