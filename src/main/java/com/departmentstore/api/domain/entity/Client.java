package com.departmentstore.api.domain.entity;

import com.departmentstore.api.domain.enums.ClientRank;
import com.departmentstore.api.domain.enums.ClientStatus;
import lombok.*;

import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class Client {

    private final Long id;
    private final Long personId;
    private final String clientCode;
    private ClientRank clientRank;
    private ClientStatus status;

    public Client(
            final Long id,
            final Long personId,
            final String clientCode,
            final ClientRank clientRank,
            final ClientStatus status)
    {
        validate(personId, clientCode, clientRank, status);
        this.id = id;
        this.personId = personId;
        this.clientCode = clientCode;
        this.clientRank = clientRank;
        this.status = status;
    }

    private void validate(
            final Long personId,
            final String clientCode,
            final ClientRank clientRank,
            final ClientStatus status)
    {
        if (personId == null) {
            throw new IllegalArgumentException("PersonId is required");
        }
        if (clientCode == null || clientCode.isBlank()) {
            throw new IllegalArgumentException("Client code is required");
        }
        if (clientRank == null) {
            throw new IllegalArgumentException("Client rank is required");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status is required");
        }
    }

    public void changeRank(final ClientRank clientRank) {
        if (clientRank == null) {
            throw new IllegalArgumentException("Client rank is required");
        }
        this.clientRank = clientRank;
    }

    public void deactivate() {
        this.status = ClientStatus.INACTIVE;
    }

    public void activate() {
        this.status = ClientStatus.ACTIVE;
    }


}
