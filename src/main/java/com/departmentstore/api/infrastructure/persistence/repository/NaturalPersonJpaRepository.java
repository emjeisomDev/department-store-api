package com.departmentstore.api.infrastructure.persistence.repository;

import com.departmentstore.api.infrastructure.persistence.entity.NaturalPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NaturalPersonJpaRepository extends JpaRepository<NaturalPersonEntity, Long>  {

    Optional<NaturalPersonEntity> findByTaxId(String taxId);
    Optional<NaturalPersonEntity> findByPersonId(Long personId);
    boolean existsByTaxId(String taxId);

}
