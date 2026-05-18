package com.departmentstore.api.domain.entity;

import com.departmentstore.api.domain.enums.ClientRank;

import java.util.Objects;

public class Client {

    private final Long id;
    private final Long personId;
    private final String clientCode;
    private ClientRank clientRank;
    private String status;

    public Client(
            final Long id,
            final Long personId,
            final String clientCode,
            final ClientRank clientRank,
            final String status
    ) {
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
            final String status
    ) {

        if (personId == null) {
            throw new IllegalArgumentException("PersonId is required");
        }

        if (clientCode == null || clientCode.isBlank()) {
            throw new IllegalArgumentException("Client code is required");
        }

        if (clientRank == null) {
            throw new IllegalArgumentException("Client rank is required");
        }

        if (status == null || status.isBlank()) {
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
        this.status = "INACTIVE";
    }

    public Long getId() {
        return id;
    }

    public Long getPersonId() {
        return personId;
    }

    public String getClientCode() {
        return clientCode;
    }

    public ClientRank getClientRank() {
        return clientRank;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Client client)) {
            return false;
        }

        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
