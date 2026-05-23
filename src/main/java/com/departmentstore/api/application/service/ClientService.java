package com.departmentstore.api.application.service;

import com.departmentstore.api.application.command.client.RegisterClientCommand;
import com.departmentstore.api.application.port.in.ManageClientUseCase;
import com.departmentstore.api.domain.entity.Client;
import com.departmentstore.api.domain.enums.ClientRank;
import com.departmentstore.api.domain.enums.ClientStatus;
import com.departmentstore.api.domain.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClientService implements ManageClientUseCase {

    private final ClientRepository repository;

    public ClientService(final ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client register(final RegisterClientCommand command) {

        Client client =
                new Client(
                        null,
                        command.personId(),
                        command.clientCode(),
                        ClientRank.BRONZE,
                        ClientStatus.ACTIVE
                );

        return repository.save(client);
    }

    @Override
    public void updateRank(final Long clientId, final ClientRank rank) {
        Client client = repository.findById(clientId).orElseThrow();
        client.changeRank(rank);
        repository.save(client);
    }

    @Override
    public void deactivate(final Long clientId) {
        Client client = repository.findById(clientId).orElseThrow();
        client.deactivate();
        repository.save(client);
    }

    @Override
    public List<Client> findActive(final int page,final int size) {
        return repository.findActive(page, size);
    }
}