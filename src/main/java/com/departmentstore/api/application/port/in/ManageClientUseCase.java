package com.departmentstore.api.application.port.in;

import com.departmentstore.api.application.command.client.RegisterClientCommand;
import com.departmentstore.api.domain.entity.Client;
import com.departmentstore.api.domain.enums.ClientRank;

import java.util.List;

public interface ManageClientUseCase {
    Client findById(Long clientId);
    Client register(RegisterClientCommand command);
    void updateRank(Long clientId, ClientRank rank);
    void deactivate(Long clientId);
    List<Client> findActive(int page, int size);
}
