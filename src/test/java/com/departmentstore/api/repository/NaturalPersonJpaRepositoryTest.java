package com.departmentstore.api.repository;

import com.departmentstore.api.infrastructure.persistence.repository.NaturalPersonJpaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class NaturalPersonJpaRepositoryTest {

    @Autowired
    private NaturalPersonJpaRepository repository;

    @Test
    void shouldCheckCpfExistence() {

        boolean exists =
                repository.existsByTaxId(
                        "11144477735"
                );

        assertNotNull(exists);
    }
}