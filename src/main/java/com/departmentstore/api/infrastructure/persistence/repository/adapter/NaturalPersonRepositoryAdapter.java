package com.departmentstore.api.infrastructure.persistence.repository.adapter;

import com.departmentstore.api.domain.entity.NaturalPerson;
import com.departmentstore.api.domain.repository.NaturalPersonRepository;
import com.departmentstore.api.domain.valueobject.CPF;
import com.departmentstore.api.infrastructure.persistence.entity.NaturalPersonEntity;
import com.departmentstore.api.infrastructure.persistence.repository.NaturalPersonJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NaturalPersonRepositoryAdapter implements NaturalPersonRepository {
    private final NaturalPersonJpaRepository repository;

    public NaturalPersonRepositoryAdapter(final NaturalPersonJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public NaturalPerson save(final NaturalPerson naturalPerson) {
        NaturalPersonEntity entity = toEntity(naturalPerson);
        NaturalPersonEntity saved = repository.save(entity);

        return toDomain(saved);
    }

    @Override
    public Optional<NaturalPerson> findById(final Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<NaturalPerson> findByPersonId(final Long personId) {
        return repository.findByPersonId(personId).map(this::toDomain);
    }

    @Override
    public Optional<NaturalPerson> findByCpf(final CPF cpf) {
        return repository.findByTaxId(cpf.getValue()).map(this::toDomain);
    }

    @Override
    public boolean existsByCpf(final CPF cpf) {
        return repository.existsByTaxId(cpf.getValue());
    }

    @Override
    public void deleteById(final Long id) {
        repository.deleteById(id);
    }

    private NaturalPerson toDomain(final NaturalPersonEntity entity) {
        return new NaturalPerson(
                entity.getId(),
                entity.getPerson().getId(),
                new CPF(entity.getTaxId()),
                entity.getBirthDate(),
                entity.getMothersName(),
                entity.getGender()
        );
    }

    private NaturalPersonEntity toEntity(final NaturalPerson domain)
    {
        NaturalPersonEntity entity = new NaturalPersonEntity();

        entity.setTaxId(domain.getCpf().getValue());
        entity.setBirthDate(domain.getBirthDate());
        entity.setMothersName(domain.getMothersName());
        entity.setGender(domain.getGender());

        return entity;
    }
}
