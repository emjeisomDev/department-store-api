package com.departmentstore.api.infrastructure.persistence.repository;

import com.departmentstore.api.domain.enums.EmployeeStatus;
import com.departmentstore.api.infrastructure.persistence.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeJpaRepository extends JpaRepository<EmployeeEntity, Long>  {

    Optional<EmployeeEntity> findByRegistrationNumber(String registrationNumber);

    List<EmployeeEntity> findByStatus(EmployeeStatus status);

    @Query("""
            SELECT e
            FROM EmployeeEntity e
            WHERE LOWER(e.person.name)
            LIKE LOWER(CONCAT('%', :name, '%'))
            """)
    List<EmployeeEntity> findByPersonNameContaining(String name);


}
