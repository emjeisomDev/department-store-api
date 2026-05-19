package com.departmentstore.api.domain.repository;

import com.departmentstore.api.domain.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    Employee save(Employee employee);
    Optional<Employee> findById(Long id);
    Optional<Employee> findByPersonId(Long personId);
    Optional<Employee> findByRegistrationNumber(String registrationNumber);
    List<Employee> findActive(int page, int size);
    boolean existsByRegistrationNumber(String registrationNumber);
    void deleteById(Long id);
}
