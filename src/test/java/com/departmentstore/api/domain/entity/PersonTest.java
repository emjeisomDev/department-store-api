package com.departmentstore.api.domain.entity;

import com.departmentstore.api.domain.enums.PersonType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void shouldCreatePersonSuccessfully() {

        Person person =
                new Person(
                        1L,
                        "João Silva",
                        PersonType.NATURAL_PERSON,
                        LocalDateTime.now(),
                        10L
                );

        assertEquals("João Silva", person.getName());
        assertEquals(PersonType.NATURAL_PERSON, person.getPersonType());
    }

    @Test
    void shouldUpdateName() {
        Person person =
                new Person(
                        1L,
                        "João",
                        PersonType.NATURAL_PERSON,
                        LocalDateTime.now(),
                        10L
                );
        person.updateName("João Atualizado");
        assertEquals("João Atualizado", person.getName());
    }
}