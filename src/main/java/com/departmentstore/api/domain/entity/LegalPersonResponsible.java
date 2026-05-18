package com.departmentstore.api.domain.entity;

import com.departmentstore.api.domain.enums.ResponsibilityType;

import java.time.LocalDate;
import java.util.Objects;

public class LegalPersonResponsible {
    private final Long id;
    private final Long legalPersonId;
    private final Long naturalPersonId;
    private final ResponsibilityType responsibilityType;
    private final LocalDate startDate;
    private LocalDate endDate;

    public LegalPersonResponsible(
            final Long id,
            final Long legalPersonId,
            final Long naturalPersonId,
            final ResponsibilityType responsibilityType,
            final LocalDate startDate)
    {
        validate(legalPersonId, naturalPersonId, responsibilityType, startDate);
        this.id = id;
        this.legalPersonId = legalPersonId;
        this.naturalPersonId = naturalPersonId;
        this.responsibilityType = responsibilityType;
        this.startDate = startDate;
    }

    private void validate(
            final Long legalPersonId,
            final Long naturalPersonId,
            final ResponsibilityType responsibilityType,
            final LocalDate startDate
    ) {

        if (legalPersonId == null) {
            throw new IllegalArgumentException("LegalPersonId is required");
        }

        if (naturalPersonId == null) {
            throw new IllegalArgumentException("NaturalPersonId is required");
        }

        if (responsibilityType == null) {
            throw new IllegalArgumentException("Responsibility type is required");
        }

        if (startDate == null) {
            throw new IllegalArgumentException("Start date is required");
        }
    }

    public void finish(
            final LocalDate endDate
    ) {

        if (endDate == null) {
            throw new IllegalArgumentException("End date is required");
        }

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public Long getLegalPersonId() {
        return legalPersonId;
    }

    public Long getNaturalPersonId() {
        return naturalPersonId;
    }

    public ResponsibilityType getResponsibilityType() {
        return responsibilityType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof LegalPersonResponsible that)) {
            return false;
        }

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
