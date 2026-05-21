package com.departmentstore.api.infrastructure.persistence.repository.adapter;

import com.departmentstore.api.domain.entity.Person;
import com.departmentstore.api.domain.enums.PersonType;
import com.departmentstore.api.domain.repository.PersonRepository;
import com.departmentstore.api.infrastructure.persistence.entity.PersonEntity;
import com.departmentstore.api.infrastructure.persistence.repository.PersonJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonRepositoryAdapter implements PersonRepository {
    private final PersonJpaRepository repository;

    public PersonRepositoryAdapter(final PersonJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Person save(final Person person) {
        PersonEntity entity = toEntity(person);
        PersonEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Person> findById(final Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Person> findAllActive(final int page, final int size) {
        return repository.findByAuditTrailDeletedAtIsNull()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Person> findByNameContaining(final String name) {
        return repository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Person> findByPersonType(final PersonType type) {
        return repository.findByPersonType(type)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public boolean existsById(final Long id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(final Long id) {
        repository.findById(id)
                .ifPresent(entity -> {
                    entity.getAuditTrail()
                            .setDeletedBy("SYSTEM");
                    repository.save(entity);
                });
    }

    private Person toDomain(final PersonEntity entity) {
        return new Person(
                entity.getId(),
                entity.getName(),
                entity.getPersonType(),
                entity.getRegistrationDate(),
                entity.getAuditTrail().getId()
        );
    }

    private PersonEntity toEntity(final Person domain)
    {
        PersonEntity entity = new PersonEntity();
        entity.setName(domain.getName());
        entity.setPersonType(domain.getPersonType());
        entity.setRegistrationDate(domain.getRegistrationDate());
        return entity;
    }


}
