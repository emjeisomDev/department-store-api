package com.departmentstore.api.application.service;

import com.departmentstore.api.application.command.person.CreateLegalPersonCommand;
import com.departmentstore.api.application.command.person.CreateNaturalPersonCommand;
import com.departmentstore.api.application.command.person.SoftDeletePersonCommand;
import com.departmentstore.api.application.command.person.UpdatePersonNameCommand;
import com.departmentstore.api.application.port.in.CreatePersonUseCase;
import com.departmentstore.api.application.port.in.ManagePersonUseCase;
import com.departmentstore.api.application.port.out.PersonSoftDeletePort;
import com.departmentstore.api.domain.entity.AuditTrail;
import com.departmentstore.api.domain.entity.LegalPerson;
import com.departmentstore.api.domain.entity.NaturalPerson;
import com.departmentstore.api.domain.entity.Person;
import com.departmentstore.api.domain.enums.PersonType;
import com.departmentstore.api.domain.exception.DuplicateTaxIdException;
import com.departmentstore.api.domain.exception.InvalidPersonOperationException;
import com.departmentstore.api.domain.exception.PersonNotFoundException;
import com.departmentstore.api.domain.exception.PersonTypeConflictException;
import com.departmentstore.api.domain.repository.AuditTrailRepository;
import com.departmentstore.api.domain.repository.LegalPersonRepository;
import com.departmentstore.api.domain.repository.LegalPersonResponsibleRepository;
import com.departmentstore.api.domain.repository.NaturalPersonRepository;
import com.departmentstore.api.domain.repository.PersonRepository;
import com.departmentstore.api.domain.valueobject.CNPJ;
import com.departmentstore.api.domain.valueobject.CPF;
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
    private final PersonSoftDeletePort personSoftDeletePort;
    private final AuditTrailRepository auditTrailRepository;

    public PersonService(
            final PersonRepository personRepository,
            final NaturalPersonRepository naturalPersonRepository,
            final LegalPersonRepository legalPersonRepository,
            final LegalPersonResponsibleRepository responsibleRepository,
            final PersonSoftDeletePort personSoftDeletePort,
            final AuditTrailRepository auditTrailRepository
    ) {
        this.personRepository = personRepository;
        this.naturalPersonRepository = naturalPersonRepository;
        this.legalPersonRepository = legalPersonRepository;
        this.responsibleRepository = responsibleRepository;
        this.personSoftDeletePort = personSoftDeletePort;
        this.auditTrailRepository = auditTrailRepository;
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

        validatePersonTypeConflict(savedPerson.getId(), PersonType.NATURAL_PERSON);

        NaturalPerson naturalPerson =
                new NaturalPerson(
                        null,
                        savedPerson.getId(),
                        cpf,
                        command.birthDate(),
                        command.mothersName(),
                        null
                );

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

        validatePersonTypeConflict(savedPerson.getId(), PersonType.LEGAL_PERSON);

        LegalPerson legalPerson =
                new LegalPerson(
                        null,
                        savedPerson.getId(),
                        cnpj,
                        command.corporateName(),
                        command.shareCapital(),
                        command.employeesQuant()
                );

        return legalPersonRepository.save(legalPerson);
    }

    @Override
    public void updateName(final UpdatePersonNameCommand command) {

        Person person = personRepository
                        .findById(command.personId())
                        .orElseThrow(() -> new PersonNotFoundException(command.personId()));

        person.updateName(command.newName());

        personRepository.save(person);
    }

    @Override
    public void softDelete(final SoftDeletePersonCommand command) {

        Person person = personRepository.findById(command.personId())
                        .orElseThrow(() -> new PersonNotFoundException(command.personId()));

        AuditTrail audit = auditTrailRepository
                          .findById(person.getAuditId())
                          .orElseThrow(() -> new InvalidPersonOperationException("Audit trail not found"));

        if (audit.getDeletedAt() != null) {
            throw new InvalidPersonOperationException("Person already deleted");
        }

        personSoftDeletePort.softDelete(command.personId(), command.deletedBy());
    }

    @Override
    public void restore(final Long personId) {

        Person person = personRepository
                        .findById(personId)
                        .orElseThrow(() -> new PersonNotFoundException(personId));

        AuditTrail audit = auditTrailRepository
                           .findById(person.getAuditId())
                           .orElseThrow(() -> new InvalidPersonOperationException("Audit trail not found"));

        if (audit.getDeletedAt() == null) {
            throw new InvalidPersonOperationException("Person is not deleted");
        }

        personSoftDeletePort.restore(personId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Person> findById(final Long personId) {
        return personRepository.findById(personId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> findAllActive(final int page, final int size) {

        return personRepository.findAllActive(page, size);
    }

    private void validatePersonTypeConflict(
            final Long personId,
            final PersonType targetType
    ) {

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