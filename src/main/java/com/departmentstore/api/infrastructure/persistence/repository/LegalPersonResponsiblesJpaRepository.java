package com.departmentstore.api.infrastructure.persistence.repository;

import com.departmentstore.api.infrastructure.persistence.entity.LegalPersonResponsiblesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LegalPersonResponsiblesJpaRepository
        extends JpaRepository<LegalPersonResponsiblesEntity, Long> {

    List<LegalPersonResponsiblesEntity> findByLegalPersonId(Long legalPersonId);
    List<LegalPersonResponsiblesEntity> findByLegalPersonIdAndEndDateIsNull(Long legalPersonId);

}
