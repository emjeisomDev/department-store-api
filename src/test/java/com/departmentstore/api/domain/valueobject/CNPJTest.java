package com.departmentstore.api.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CNPJTest {

    @Test
    void shouldCreateValidCnpj() {
        CNPJ cnpj = new CNPJ("11444777000161");
        assertEquals("11444777000161", cnpj.getValue());
    }

    @Test
    void shouldFormatCnpjCorrectly() {
        CNPJ cnpj = new CNPJ("11444777000161");
        assertEquals("11.444.777/0001-61", cnpj.getFormatted());
    }

}
