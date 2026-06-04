package com.departmentstore.api.application.service;

import com.departmentstore.api.application.command.person.CreateNaturalPersonCommand;
import com.departmentstore.api.domain.entity.Person;
import com.departmentstore.api.domain.enums.Gender;
import com.departmentstore.api.domain.enums.PersonType;
import com.departmentstore.api.domain.exception.DuplicateTaxIdException;
import com.departmentstore.api.domain.repository.*;
import com.departmentstore.api.domain.valueobject.CPF;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private NaturalPersonRepository naturalPersonRepository;

    @Mock
    private LegalPersonRepository legalPersonRepository;

    @Mock
    private LegalPersonResponsibleRepository responsibleRepository;

    @Mock
    private AuditTrailRepository auditTrailRepository;

    @Mock
    private com.departmentstore.api.application.port.out.PersonSoftDeletePort
            personSoftDeletePort;

    @InjectMocks
    private PersonService service;

    @Test
    void shouldCreateNaturalPersonSuccessfully() {

        CreateNaturalPersonCommand command =
                new CreateNaturalPersonCommand(
                        "João",
                        "11144477735",
                        LocalDate.of(1990, 1, 1),
                        "Maria",
                        Gender.MALE
                );

        when(personRepository.save(any(Person.class)))
                .thenReturn(
                        new Person(
                                1L,
                                "João",
                                PersonType.NATURAL_PERSON,
                                LocalDateTime.now(),
                                null
                        )
                );

        when(naturalPersonRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        service.createNaturalPerson(command);

        verify(personRepository).save(any(Person.class));
        verify(naturalPersonRepository).save(any());
    }

    @Test
    void shouldThrowDuplicateTaxIdException() {

        CreateNaturalPersonCommand command =
                new CreateNaturalPersonCommand(
                        "João Silva",
                        "52998224725",
                        LocalDate.of(1990, 1, 1),
                        "Maria Silva",
                        Gender.MALE
                );

        when(naturalPersonRepository.existsByCpf(any(CPF.class)))
                .thenReturn(true);

        assertThrows(
                DuplicateTaxIdException.class,
                () -> service.createNaturalPerson(command)
        );

        verify(personRepository, never()).save(any());
    }
}