package com.departmentstore.api.infrastructure.persistence.repository.adapter;

import com.departmentstore.api.domain.entity.Client;
import com.departmentstore.api.domain.enums.ClientStatus;
import com.departmentstore.api.domain.repository.ClientRepository;
import com.departmentstore.api.infrastructure.persistence.entity.ClientEntity;
import com.departmentstore.api.infrastructure.persistence.repository.ClientJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClientRepositoryAdapter implements ClientRepository {
    private final ClientJpaRepository repository;

    public ClientRepositoryAdapter(final ClientJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client save(final Client client) {
        ClientEntity entity = toEntity(client);
        ClientEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Client> findById(final Long id){
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Client> findByPersonId(final Long personId) {
        return repository.findAll()
                .stream()
                .filter(c -> c.getPerson().getId().equals(personId))
                .findFirst()
                .map(this::toDomain);
    }

    @Override
    public Optional<Client> findByClientCode(final String clientCode) {
        return repository
                .findByClientCode(clientCode)
                .map(this::toDomain);
    }

    @Override
    public List<Client> findActive(final int page, final int size) {
        return repository.findByStatus(ClientStatus.ACTIVE)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deleteById(final Long id) {
        repository.deleteById(id);
    }

    private Client toDomain(final ClientEntity entity) {
        return new Client(
                entity.getId(),
                entity.getPerson().getId(),
                entity.getClientCode(),
                entity.getClientRank(),
                entity.getStatus());
    }

    private ClientEntity toEntity(final Client domain) {
        ClientEntity entity = new ClientEntity();
        entity.setClientCode(domain.getClientCode());
        entity.setClientRank(domain.getClientRank());
        entity.setStatus(domain.getStatus());
        return entity;
    }
}
