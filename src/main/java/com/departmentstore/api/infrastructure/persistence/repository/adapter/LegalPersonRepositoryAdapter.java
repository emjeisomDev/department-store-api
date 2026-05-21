package com.departmentstore.api.infrastructure.persistence.repository.adapter;

import com.departmentstore.api.domain.entity.LegalPerson;
import com.departmentstore.api.domain.repository.LegalPersonRepository;
import com.departmentstore.api.domain.valueobject.CNPJ;
import com.departmentstore.api.infrastructure.persistence.entity.LegalPersonEntity;
import com.departmentstore.api.infrastructure.persistence.repository.LegalPersonJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LegalPersonRepositoryAdapter implements LegalPersonRepository  {
    private final LegalPersonJpaRepository repository;

    public LegalPersonRepositoryAdapter( final LegalPersonJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public LegalPerson save(final LegalPerson legalPerson) {
        LegalPersonEntity entity = toEntity(legalPerson);
        LegalPersonEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<LegalPerson> findById(final Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<LegalPerson> findByPersonId(final Long personId) {
        return repository.findByPersonId(personId).map(this::toDomain);
    }

    @Override
    public Optional<LegalPerson> findByCnpj(final CNPJ cnpj) {
        return repository.findByTaxId(cnpj.getValue()).map(this::toDomain);
    }

    @Override
    public boolean existsByCnpj(final CNPJ cnpj) {
        return repository.existsByTaxId(cnpj.getValue());
    }

    @Override
    public void deleteById(final Long id) {
        repository.deleteById(id);
    }

    private LegalPerson toDomain(final LegalPersonEntity entity) {
        return new LegalPerson(
                entity.getId(),
                entity.getPerson().getId(),
                new CNPJ(entity.getTaxId()),
                entity.getCorporateName(),
                entity.getShareCapital(),
                entity.getEmployeesQuant()
        );
    }

    private LegalPersonEntity toEntity(final LegalPerson domain) {
        LegalPersonEntity entity = new LegalPersonEntity();
        entity.setTaxId(domain.getCnpj().getValue());
        entity.setCorporateName(domain.getCorporateName());
        entity.setShareCapital(domain.getShareCapital());
        entity.setEmployeesQuant(domain.getEmployeesQuant());
        return entity;
    }
}
