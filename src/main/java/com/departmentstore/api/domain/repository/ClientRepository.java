package com.departmentstore.api.domain.repository;

import com.departmentstore.api.domain.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Client save(Client client);
    Optional<Client> findById(Long id);
    Optional<Client> findByPersonId(Long personId);
    Optional<Client> findByClientCode(String clientCode);
    List<Client> findActive(int page, int size);
    void deleteById(Long id);
}
