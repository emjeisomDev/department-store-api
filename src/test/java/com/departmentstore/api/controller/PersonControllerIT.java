package com.departmentstore.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PersonControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnBadRequestForInvalidCpf() throws Exception {

        String payload = """
        {
          "name":"Joao Silva",
          "cpf":"12345678900",
          "birthDate":"1990-01-01",
          "mothersName":"Maria Silva",
          "gender":"MALE"
        }
        """;

        mockMvc.perform(post("/persons/natural")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload))
                .andExpect(status().isBadRequest());
    }
}