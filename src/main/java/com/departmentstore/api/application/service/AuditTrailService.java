package com.departmentstore.api.application.service;

import com.departmentstore.api.application.port.in.GetAuditTrailUseCase;
import com.departmentstore.api.domain.entity.AuditTrail;
import com.departmentstore.api.domain.entity.Person;
import com.departmentstore.api.domain.exception.PersonNotFoundException;
import com.departmentstore.api.domain.repository.AuditTrailRepository;
import com.departmentstore.api.domain.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditTrailService
        implements GetAuditTrailUseCase {

    private final PersonRepository personRepository;
    private final AuditTrailRepository auditTrailRepository;

    public AuditTrailService(
            final PersonRepository personRepository,
            final AuditTrailRepository auditTrailRepository
    ) {
        this.personRepository = personRepository;
        this.auditTrailRepository = auditTrailRepository;
    }

    @Override
    public AuditTrail findByPersonId(final Long personId) {
        Person person = personRepository
                        .findById(personId)
                        .orElseThrow(() -> new PersonNotFoundException(personId));

        return auditTrailRepository
                .findById(person.getAuditId())
                .orElseThrow(() -> new RuntimeException("Audit trail not found"));
    }

}