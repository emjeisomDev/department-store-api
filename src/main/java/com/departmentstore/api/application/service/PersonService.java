package com.departmentstore.api.application.service;

import com.departmentstore.api.application.command.person.*;
import com.departmentstore.api.application.port.in.*;
import com.departmentstore.api.domain.entity.*;
import com.departmentstore.api.domain.enums.PersonType;
import com.departmentstore.api.domain.exception.DuplicateTaxIdException;
import com.departmentstore.api.domain.exception.PersonTypeConflictException;
import com.departmentstore.api.domain.repository.*;
import com.departmentstore.api.domain.valueobject.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonService implements CreatePersonUseCase, ManagePersonUseCase {

    private final PersonRepository personRepository;
    private final NaturalPersonRepository naturalPersonRepository;
    private final LegalPersonRepository legalPersonRepository;
    private final LegalPersonResponsibleRepository responsibleRepository;

    public PersonService(
            final PersonRepository personRepository,
            final NaturalPersonRepository naturalPersonRepository,
            final LegalPersonRepository legalPersonRepository,
            final LegalPersonResponsibleRepository responsibleRepository
    ) {
        this.personRepository = personRepository;
        this.naturalPersonRepository = naturalPersonRepository;
        this.legalPersonRepository = legalPersonRepository;
        this.responsibleRepository = responsibleRepository;
    }

    @Override
    public NaturalPerson createNaturalPerson(final CreateNaturalPersonCommand command) {
        CPF cpf = new CPF(command.cpf());
        if (naturalPersonRepository.existsByCpf(cpf)) {
            throw new DuplicateTaxIdException(cpf.getValue());
        }

        Person person = new Person(
                null,
                command.name(),
                PersonType.NATURAL_PERSON,
                LocalDateTime.now(),
                null
        );

        Person savedPerson = personRepository.save(person);

        if (legalPersonRepository.findByPersonId(savedPerson.getId()).isPresent()) {
            throw new PersonTypeConflictException("Person already linked to LegalPerson");
        }

        NaturalPerson naturalPerson =
                new NaturalPerson(
                        null,
                        savedPerson.getId(),
                        cpf,
                        command.birthDate(),
                        command.mothersName(),
                        null
                );

        validatePersonTypeConflict(savedPerson.getId(), PersonType.NATURAL_PERSON);

        return naturalPersonRepository.save(naturalPerson);
    }

    @Override
    public LegalPerson createLegalPerson(final CreateLegalPersonCommand command) {
        CNPJ cnpj = new CNPJ(command.cnpj());
        if (legalPersonRepository.existsByCnpj(cnpj)) {
            throw new DuplicateTaxIdException(cnpj.getValue());
        }

        Person person = new Person(
                null,
                command.name(),
                PersonType.LEGAL_PERSON,
                LocalDateTime.now(),
                null
        );

        Person savedPerson = personRepository.save(person);
        if (naturalPersonRepository.findByPersonId(savedPerson.getId()).isPresent()) {
            throw new PersonTypeConflictException("Person already linked to NaturalPerson");
        }

        LegalPerson legalPerson =
                new LegalPerson(
                        null,
                        savedPerson.getId(),
                        cnpj,
                        command.corporateName(),
                        command.shareCapital(),
                        command.employeesQuant()
                );

        validatePersonTypeConflict(savedPerson.getId(), PersonType.LEGAL_PERSON);
        return legalPersonRepository.save(legalPerson);
    }

    @Override
    public void updateName(final UpdatePersonNameCommand command) {
        Person person = personRepository.findById(command.personId()).orElseThrow();
        person.updateName(command.newName());
        personRepository.save(person);
    }

    @Override
    public void softDelete(final SoftDeletePersonCommand command) {
        Person person = personRepository.findById(command.personId()).orElseThrow();
        person.markAsDeleted(command.deletedBy());
        personRepository.save(person);
    }

    @Override
    public void restore(final Long personId) {
        Person person = personRepository.findById(personId).orElseThrow();
        person.restore();
        personRepository.save(person);
    }

    @Override
    public Optional<Person> findById(final Long personId) {
        return personRepository.findById(personId);
    }

    @Override
    public List<Person> findAllActive(final int page, final int size) {
        return personRepository.findAllActive(page, size);
    }

    private void validatePersonTypeConflict(final Long personId, final PersonType targetType) {

        if (PersonType.NATURAL_PERSON.equals(targetType)
                && legalPersonRepository
                .findByPersonId(personId)
                .isPresent()) {

            throw new PersonTypeConflictException("Person already linked to LegalPerson");
        }

        if (PersonType.LEGAL_PERSON.equals(targetType)
                && naturalPersonRepository
                .findByPersonId(personId)
                .isPresent()) {

            throw new PersonTypeConflictException("Person already linked to NaturalPerson");
        }
    }
}