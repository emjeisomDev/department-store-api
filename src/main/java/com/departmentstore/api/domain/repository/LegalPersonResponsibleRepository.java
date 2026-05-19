package com.departmentstore.api.domain.repository;

import com.departmentstore.api.domain.entity.LegalPersonResponsible;

import java.util.List;

public interface LegalPersonResponsibleRepository {
    LegalPersonResponsible save(LegalPersonResponsible responsible);
    List<LegalPersonResponsible> findByLegalPersonId(Long legalPersonId);
    List<LegalPersonResponsible> findActiveByLegalPersonId(Long legalPersonId);
    void deleteById(Long id);
}
