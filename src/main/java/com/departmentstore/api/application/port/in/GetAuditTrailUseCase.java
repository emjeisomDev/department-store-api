package com.departmentstore.api.application.port.in;

import com.departmentstore.api.domain.entity.AuditTrail;

public interface GetAuditTrailUseCase {
    AuditTrail findByPersonId(Long personId);
}
