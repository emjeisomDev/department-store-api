package com.departmentstore.api.application.command.person;

import com.departmentstore.api.application.command.responsible.ResponsibleCommand;

import java.math.BigDecimal;
import java.util.List;

public record CreateLegalPersonCommand(
        String name,
        String cnpj,
        String corporateName,
        BigDecimal shareCapital,
        Integer employeesQuant,
        List<ResponsibleCommand> responsibles
) {
}
