package com.departmentstore.api.domain.entity;

import com.departmentstore.api.domain.valueobject.CNPJ;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
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

        if (shareCapital.signum() < 0) {

            throw new IllegalArgumentException("Share capital cannot be negative");
        }

        if (employeesQuant < 0) {
            throw new IllegalArgumentException("Employees quantity cannot be negative");
        }
    }

}
