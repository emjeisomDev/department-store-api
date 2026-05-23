package com.departmentstore.api.application.command.person;

public record UpdatePersonNameCommand(
        Long personId,
        String newName
) {
}
