package com.departmentstore.api.application.command.person;

import com.departmentstore.api.domain.enums.Gender;

import java.time.LocalDate;

public record CreateNaturalPersonCommand(
        String name,
        String cpf,
        LocalDate birthDate,
        String mothersName,
        Gender gender
) {}
