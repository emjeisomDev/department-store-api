package com.departmentstore.api.application.command.responsible;

public record AddResponsibleCommand(
        Long legalPersonId,
        Long naturalPersonId,
        String responsibilityType
) {
}
