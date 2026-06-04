package com.departmentstore.api.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPFTest {

    @Test
    void shouldCreateValidCpf() {
        CPF cpf = new CPF("52998224725");
        assertEquals("52998224725", cpf.getValue());
    }

    @Test
    void shouldThrowExceptionWhenCpfContainsLetters() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new CPF("12345ABC901")
        );
    }

    @Test
    void shouldThrowExceptionWhenCpfHasTenDigits() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new CPF("1234567890")
        );
    }

    @Test
    void shouldFormatCpfCorrectly() {
        CPF cpf = new CPF("52998224725");
        assertEquals("529.982.247-25", cpf.getFormatted());
    }
}