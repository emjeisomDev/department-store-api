package com.departmentstore.api.domain.entity;

import com.departmentstore.api.domain.enums.EmployeeRole;
import com.departmentstore.api.domain.enums.EmployeeStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void shouldTerminateEmployee() {
        Employee employee =
                new Employee(
                        1L,
                        1L,
                        "EMP001",
                        LocalDate.now(),
                        null,
                        null,
                        EmployeeRole.GENERAL,
                        EmployeeStatus.ACTIVE
                );

        employee.terminate(LocalDate.now().plusDays(1), "Contract ended");

        assertEquals(EmployeeStatus.TERMINATED, employee.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenTerminationDateIsBeforeHireDate() {

        Employee employee =
                new Employee(
                        1L,
                        1L,
                        "EMP001",
                        LocalDate.now(),
                        null,
                        null,
                        EmployeeRole.GENERAL,
                        EmployeeStatus.ACTIVE
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> employee.terminate(
                        LocalDate.now().minusDays(1),
                        "Invalid"
                )
        );
    }
}