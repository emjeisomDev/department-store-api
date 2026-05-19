package com.departmentstore.api.domain.repository;

import com.departmentstore.api.domain.entity.NaturalPerson;
import com.departmentstore.api.domain.valueobject.CPF;

import java.util.Optional;

public interface NaturalPersonRepository {
    NaturalPerson save(NaturalPerson naturalPerson);
    Optional<NaturalPerson> findById(Long id);
    Optional<NaturalPerson> findByPersonId(Long personId);
    Optional<NaturalPerson> findByCpf(CPF cpf);
    boolean existsByCpf(CPF cpf);
    void deleteById(Long id);
}
