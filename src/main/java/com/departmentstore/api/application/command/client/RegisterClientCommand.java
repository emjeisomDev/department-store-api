package com.departmentstore.api.application.command.client;

public record RegisterClientCommand(
        Long personId,
        String clientCode,
        String clientRank
) {
}
