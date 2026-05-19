package com.departmentstore.api.domain.repository;

import com.departmentstore.api.domain.entity.LegalPerson;
import com.departmentstore.api.domain.valueobject.CNPJ;

import java.util.Optional;

public interface LegalPersonRepository {
    LegalPerson save(LegalPerson legalPerson);
    Optional<LegalPerson> findById(Long id);
    Optional<LegalPerson> findByPersonId(Long personId);
    Optional<LegalPerson> findByCnpj(CNPJ cnpj);
    boolean existsByCnpj(CNPJ cnpj);
    void deleteById(Long id);
}
