package com.departmentstore.api.infrastructure.persistence.repository;

import com.departmentstore.api.domain.enums.ClientStatus;
import com.departmentstore.api.infrastructure.persistence.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientJpaRepository extends JpaRepository<ClientEntity, Long> {

    Optional<ClientEntity> findByClientCode(String clientCode);
    List<ClientEntity> findByStatus(ClientStatus status);

}
