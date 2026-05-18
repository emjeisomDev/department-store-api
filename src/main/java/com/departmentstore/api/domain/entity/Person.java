package com.departmentstore.api.domain.entity;

import com.departmentstore.api.domain.enums.PersonType;

import java.time.LocalDateTime;
import java.util.Objects;

public class Person {
    private final Long id;
    private String name;
    private final PersonType personType;
    private final LocalDateTime registrationDate;
    private final Long auditId;
    private boolean deleted;
    private String deletedBy;

    public Person(
            final Long id,
            final String name,
            final PersonType personType,
            final LocalDateTime registrationDate,
            final Long auditId)
    {
        this.id = id;
        this.name = name;
        this.personType = personType;
        this.registrationDate = registrationDate;
        this.auditId = auditId;
        this.deleted = false;
    }

    private void validate(
            final String name,
            final PersonType personType,
            final LocalDateTime registrationDate,
            final Long auditId
    ) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Person name is required"
            );
        }

        if (personType == null) {
            throw new IllegalArgumentException(
                    "Person type is required"
            );
        }

        if (registrationDate == null) {
            throw new IllegalArgumentException(
                    "Registration date is required"
            );
        }

        if (auditId == null) {
            throw new IllegalArgumentException(
                    "Audit id is required"
            );
        }
    }

    public boolean isNaturalPerson() {
        return PersonType.NATURAL_PERSON.equals(personType);
    }

    public boolean isLegalPerson() {
        return PersonType.LEGAL_PERSON.equals(personType);
    }

    public void updateName(final String newName) {

        if (deleted) {
            throw new IllegalStateException(
                    "Deleted person cannot be modified"
            );
        }

        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException(
                    "Name cannot be empty"
            );
        }

        this.name = newName;
    }

    public void markAsDeleted(final String deletedBy) {

        if (deleted) {
            throw new IllegalStateException(
                    "Person already deleted"
            );
        }

        if (deletedBy == null || deletedBy.isBlank()) {
            throw new IllegalArgumentException(
                    "DeletedBy is required"
            );
        }

        this.deleted = true;
        this.deletedBy = deletedBy;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public Long getAuditId() {
        return auditId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Person person)) {
            return false;
        }

        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }




}
