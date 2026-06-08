package com.departmentstore.api.repository;

import com.departmentstore.api.infrastructure.persistence.repository.LegalPersonJpaRepository;
import com.departmentstore.api.infrastructure.persistence.repository.PersonJpaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class PersonJpaRepositoryTest {

    @Autowired
    private PersonJpaRepository repository;

    @Test
    void shouldFindActivePersons() {
        assertNotNull(repository.findByAuditTrailDeletedAtIsNull());
    }

    @Test
    void shouldFindCompletePersonsView() {
        assertNotNull(repository.findCompletePersons());
    }
}
