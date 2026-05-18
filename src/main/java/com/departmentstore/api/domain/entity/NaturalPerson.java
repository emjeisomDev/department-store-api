package com.departmentstore.api.domain.entity;

import com.departmentstore.api.domain.enums.Gender;
import com.departmentstore.api.domain.valueobject.CPF;

import java.time.LocalDate;
import java.util.Objects;

public class NaturalPerson {
    private final Long id;
    private final Long personId;
    private final CPF cpf;
    private final LocalDate birthDate;
    private final String mothersName;
    private final Gender gender;

    public NaturalPerson(
            final Long id,
            final Long personId,
            final CPF cpf,
            final LocalDate birthDate,
            final String mothersName,
            final Gender gender
    ){
        validate(personId, cpf, birthDate, mothersName, gender);
        this.id = id;
        this.personId = personId;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.mothersName = mothersName;
        this.gender = gender;
    }

    private void validate(final Long personId, final CPF cpf,
            final LocalDate birthDate, final String mothersName, final Gender gender)
    {

        if (personId == null) {
            throw new IllegalArgumentException("PersonId is required");
        }

        if (cpf == null) {
            throw new IllegalArgumentException("CPF is required");
        }

        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date is required");
        }

        if (!birthDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date must be in the past");
        }

        if (mothersName == null || mothersName.isBlank()) {
            throw new IllegalArgumentException("Mother's name is required");
        }

        if (gender == null) {
            throw new IllegalArgumentException("Gender is required");
        }
    }

    public Long getId() {
        return id;
    }

    public Long getPersonId() {
        return personId;
    }

    public CPF getCpf() {
        return cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getMothersName() {
        return mothersName;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof NaturalPerson that)) {
            return false;
        }

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}