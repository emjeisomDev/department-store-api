package com.departmentstore.api.infrastructure.persistence.repository;

import com.departmentstore.api.infrastructure.persistence.entity.LegalPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LegalPersonJpaRepository extends JpaRepository<LegalPersonEntity, Long> {

    Optional<LegalPersonEntity> findByTaxId(String taxId);
    Optional<LegalPersonEntity> findByPersonId(Long personId);
    boolean existsByTaxId(String taxId);

}
