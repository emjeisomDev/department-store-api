package com.departmentstore.api.application.command.responsible;

import com.departmentstore.api.domain.enums.ResponsibilityType;

public record AddResponsibleCommand(
        Long legalPersonId,
        Long naturalPersonId,
        String responsibilityType
) {
}
