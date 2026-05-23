package com.departmentstore.api.application.command.person;

public record SoftDeletePersonCommand(
        Long personId,
        String deletedBy
) {
}
