package com.departmentstore.api.infrastructure.persistence.repository.adapter;

import com.departmentstore.api.domain.entity.Employee;
import com.departmentstore.api.domain.enums.EmployeeStatus;
import com.departmentstore.api.domain.repository.EmployeeRepository;
import com.departmentstore.api.infrastructure.persistence.entity.EmployeeEntity;
import com.departmentstore.api.infrastructure.persistence.repository.EmployeeJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EmployeeRepositoryAdapter implements EmployeeRepository {

    private final EmployeeJpaRepository repository;

    public EmployeeRepositoryAdapter(final EmployeeJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Employee save(final Employee employee) {
        EmployeeEntity entity = toEntity(employee);
        EmployeeEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Employee> findById(final Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Employee> findByPersonId(final Long personId) {
        return repository.findAll()
                .stream()
                .filter(e -> e.getPerson().getId().equals(personId))
                .findFirst()
                .map(this::toDomain);
    }

    @Override
    public Optional<Employee>
    findByRegistrationNumber(final String registrationNumber) {
        return repository
                .findByRegistrationNumber(registrationNumber)
                .map(this::toDomain);
    }

    @Override
    public List<Employee> findActive(final int page, final int size) {
        return repository
                .findByStatus(EmployeeStatus.ACTIVE)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public boolean existsByRegistrationNumber(final String registrationNumber) {
        return repository
                .findByRegistrationNumber(registrationNumber)
                .isPresent();
    }

    @Override
    public void deleteById(final Long id) {
        repository.deleteById(id);
    }

    private Employee toDomain(final EmployeeEntity entity) {
        return new Employee(
                entity.getId(),
                entity.getPerson().getId(),
                entity.getRegistrationNumber(),
                entity.getHireDate(),
                entity.getEmployeeRole(),
                entity.getStatus()
        );
    }

    private EmployeeEntity toEntity(final Employee domain) {
        EmployeeEntity entity = new EmployeeEntity();

        entity.setRegistrationNumber(domain.getRegistrationNumber());
        entity.setHireDate(domain.getHireDate());
        entity.setTerminationDate(domain.getTerminationDate());
        entity.setTerminationReason(domain.getTerminationReason());
        entity.setEmployeeRole(domain.getEmployeeRole());
        entity.setStatus(domain.getStatus());

        return entity;
    }
}
