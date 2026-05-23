package com.departmentstore.api.application.command.person;

import java.time.LocalDate;

public record CreateNaturalPersonCommand(
        String name,
        String cpf,
        LocalDate birthDate,
        String mothersName,
        String gender
) {}
