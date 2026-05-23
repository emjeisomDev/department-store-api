package com.departmentstore.api.application.command.responsible;

public record ResponsibleCommand(
        Long naturalPersonId,
        String responsibilityType
) {
}
