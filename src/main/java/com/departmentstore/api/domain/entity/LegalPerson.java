package com.departmentstore.api.domain.entity;

import com.departmentstore.api.domain.valueobject.CNPJ;

import java.math.BigDecimal;
import java.util.Objects;

public class LegalPerson {

    private final Long id;
    private final Long personId;
    private final CNPJ cnpj;
    private final String corporateName;
    private final BigDecimal shareCapital;
    private final Integer employeesQuant;

    public LegalPerson(
            final Long id,
            final Long personId,
            final CNPJ cnpj,
            final String corporateName,
            final BigDecimal shareCapital,
            final Integer employeesQuant
    ) {
        validate(personId, cnpj, corporateName, shareCapital, employeesQuant);
        this.id = id;
        this.personId = personId;
        this.cnpj = cnpj;
        this.corporateName = corporateName;
        this.shareCapital = shareCapital;
        this.employeesQuant = employeesQuant;
    }

    private void validate(final Long personId, final CNPJ cnpj, final String corporateName,
            final BigDecimal shareCapital, final Integer employeesQuant)
    {

        if (personId == null) {
            throw new IllegalArgumentException("PersonId is required");
        }

        if (cnpj == null) {
            throw new IllegalArgumentException("CNPJ is required");
        }

        if (corporateName == null || corporateName.isBlank()) {
            throw new IllegalArgumentException("Corporate name is required");
        }

        if (shareCapital == null || shareCapital.signum() < 0) {

            throw new IllegalArgumentException("Share capital cannot be negative");
        }

        if (employeesQuant == null || employeesQuant < 0) {
            throw new IllegalArgumentException("Employees quantity cannot be negative");
        }
    }
    public Long getId() {
        return id;
    }

    public Long getPersonId() {
        return personId;
    }

    public CNPJ getCnpj() {
        return cnpj;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public BigDecimal getShareCapital() {
        return shareCapital;
    }

    public Integer getEmployeesQuant() {
        return employeesQuant;
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof LegalPerson that)) {
            return false;
        }

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
