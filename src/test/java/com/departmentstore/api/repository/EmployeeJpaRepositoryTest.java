package com.departmentstore.api.repository;

import com.departmentstore.api.domain.enums.EmployeeStatus;
import com.departmentstore.api.infrastructure.persistence.repository.EmployeeJpaRepository;
import com.departmentstore.api.infrastructure.persistence.repository.LegalPersonJpaRepository;
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
class EmployeeJpaRepositoryTest {

    @Autowired
    private EmployeeJpaRepository repository;

    @Test
    void shouldFindByStatus() {
        assertNotNull(repository.findByStatus(EmployeeStatus.ACTIVE));
    }

    @Test
    void shouldFindByPersonName() {
        assertNotNull(repository.findByPersonNameContaining("Joao"));
    }
}
