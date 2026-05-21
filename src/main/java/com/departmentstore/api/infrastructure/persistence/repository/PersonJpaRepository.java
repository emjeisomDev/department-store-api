package com.departmentstore.api.infrastructure.persistence.repository;

import com.departmentstore.api.domain.enums.PersonType;
import com.departmentstore.api.infrastructure.persistence.entity.PersonEntity;
import com.departmentstore.api.infrastructure.persistence.projection.PersonCompleteView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonJpaRepository extends JpaRepository<PersonEntity, Long> {
    List<PersonEntity> findByNameContainingIgnoreCase(String name);
    List<PersonEntity> findByPersonType(PersonType personType);
    List<PersonEntity> findByAuditTrailDeletedAtIsNull();

    @Query(
            value = """
                    SELECT *
                    FROM vw_person_complete
                    """,
            nativeQuery = true
    )
    List<PersonCompleteView> findCompletePersons();
}
