package com.departmentstore.api.domain.repository;

import com.departmentstore.api.domain.entity.LegalPersonResponsible;

import java.util.List;
import java.util.Optional;

public interface LegalPersonResponsibleRepository {
    LegalPersonResponsible save(LegalPersonResponsible responsible);
    Optional<LegalPersonResponsible> findById(Long id);
    List<LegalPersonResponsible> findByLegalPersonId(Long legalPersonId);
    List<LegalPersonResponsible> findActiveByLegalPersonId(Long legalPersonId);
    void deleteById(Long id);
}
