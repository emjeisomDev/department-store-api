package com.departmentstore.api.application.service;

import com.departmentstore.api.application.command.employee.HireEmployeeCommand;
import com.departmentstore.api.application.command.employee.TerminateEmployeeCommand;
import com.departmentstore.api.application.port.in.ManageEmployeeUseCase;
import com.departmentstore.api.domain.entity.Employee;
import com.departmentstore.api.domain.entity.Person;
import com.departmentstore.api.domain.enums.EmployeeRole;
import com.departmentstore.api.domain.enums.EmployeeStatus;
import com.departmentstore.api.domain.enums.PersonType;
import com.departmentstore.api.domain.exception.DuplicateRegistrationException;
import com.departmentstore.api.domain.exception.EmployeeMustBeNaturalPersonException;
import com.departmentstore.api.domain.exception.EmployeeNotFoundException;
import com.departmentstore.api.domain.repository.EmployeeRepository;
import com.departmentstore.api.domain.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService implements ManageEmployeeUseCase {

    private final EmployeeRepository repository;
    private final PersonRepository personRepository;

    public EmployeeService(final EmployeeRepository repository, final PersonRepository personRepository) {
        this.repository = repository;
        this.personRepository = personRepository;
    }

    @Override
    public Employee findById(final Long employeeId) {

        return repository.findById(employeeId).orElseThrow(() ->
                new EmployeeNotFoundException(employeeId));
    }

    @Override
    public Employee hire(final HireEmployeeCommand command) {
        Person person = personRepository.findById(command.personId()).orElseThrow();

        if (!PersonType.NATURAL_PERSON.equals(person.getPersonType())) {
            throw new EmployeeMustBeNaturalPersonException();
        }

        if (repository.existsByRegistrationNumber(command.registrationNumber())) {
            throw new DuplicateRegistrationException(command.registrationNumber());
        }

        Employee employee =
                new Employee(
                        null,
                        person.getId(),
                        command.registrationNumber(),
                        command.hireDate(),
                        null,
                        null,
                        EmployeeRole.GENERAL,
                        EmployeeStatus.ACTIVE
                );

        return repository.save(employee);
    }

    @Override
    public void terminate(final TerminateEmployeeCommand command) {
        Employee employee = repository.findById(command.employeeId()).orElseThrow();
        employee.terminate(command.terminationDate(), command.terminationReason());
        repository.save(employee);
    }

    @Override
    public List<Employee> findActive(final int page, final int size) {
        return repository.findActive(page, size);
    }

    @Override
    public void updateRole(final Long employeeId, final EmployeeRole role) {
        Employee employee = repository.findById(employeeId).orElseThrow();
        employee.changeRole(role);
        repository.save(employee);
    }
}