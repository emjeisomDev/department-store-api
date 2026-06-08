package com.departmentstore.api.repository;

import com.departmentstore.api.infrastructure.persistence.repository.LegalPersonResponsiblesJpaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class LegalPersonResponsiblesJpaRepositoryTest {

    @Autowired
    private LegalPersonResponsiblesJpaRepository repository;

    @Test
    void shouldFindActiveResponsibles() {
        assertNotNull(repository.findByLegalPersonIdAndEndDateIsNull(1L));
    }
}