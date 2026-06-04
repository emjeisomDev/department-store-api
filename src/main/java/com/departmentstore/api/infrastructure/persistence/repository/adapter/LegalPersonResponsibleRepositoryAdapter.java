package com.departmentstore.api.infrastructure.persistence.repository.adapter;

import com.departmentstore.api.domain.entity.LegalPersonResponsible;
import com.departmentstore.api.domain.repository.LegalPersonResponsibleRepository;
import com.departmentstore.api.infrastructure.persistence.entity.LegalPersonEntity;
import com.departmentstore.api.infrastructure.persistence.entity.LegalPersonResponsiblesEntity;
import com.departmentstore.api.infrastructure.persistence.entity.NaturalPersonEntity;
import com.departmentstore.api.infrastructure.persistence.repository.LegalPersonResponsiblesJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LegalPersonResponsibleRepositoryAdapter
        implements LegalPersonResponsibleRepository {

    private final LegalPersonResponsiblesJpaRepository repository;

    public LegalPersonResponsibleRepositoryAdapter(
            final LegalPersonResponsiblesJpaRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public LegalPersonResponsible save(
            final LegalPersonResponsible responsible
    ) {

        LegalPersonResponsiblesEntity entity =
                toEntity(responsible);

        LegalPersonResponsiblesEntity saved =
                repository.save(entity);

        return toDomain(saved);
    }

    @Override
    public Optional<LegalPersonResponsible> findById(
            final Long id
    ) {

        return repository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<LegalPersonResponsible> findByLegalPersonId(
            final Long legalPersonId
    ) {

        return repository.findByLegalPersonId(
                        legalPersonId
                )
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<LegalPersonResponsible> findActiveByLegalPersonId(
            final Long legalPersonId
    ) {

        return repository
                .findByLegalPersonIdAndEndDateIsNull(
                        legalPersonId
                )
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deleteById(
            final Long id
    ) {

        repository.deleteById(id);
    }

    private LegalPersonResponsible toDomain(
            final LegalPersonResponsiblesEntity entity
    ) {

        return new LegalPersonResponsible(
                entity.getId(),
                entity.getLegalPerson().getId(),
                entity.getNaturalPerson().getId(),
                entity.getResponsibilityType(),
                entity.getStartDate(),
                entity.getEndDate()
        );
    }

    private LegalPersonResponsiblesEntity toEntity(
            final LegalPersonResponsible domain
    ) {

        LegalPersonEntity legalPerson =
                new LegalPersonEntity();

        legalPerson.setId(
                domain.getLegalPersonId()
        );

        NaturalPersonEntity naturalPerson =
                new NaturalPersonEntity();

        naturalPerson.setId(
                domain.getNaturalPersonId()
        );

        return LegalPersonResponsiblesEntity
                .builder()
                .id(domain.getId())
                .legalPerson(legalPerson)
                .naturalPerson(naturalPerson)
                .responsibilityType(
                        domain.getResponsibilityType()
                )
                .startDate(
                        domain.getStartDate()
                )
                .endDate(
                        domain.getEndDate()
                )
                .build();
    }
}