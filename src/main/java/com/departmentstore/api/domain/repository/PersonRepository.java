package com.departmentstore.api.domain.repository;

import com.departmentstore.api.domain.entity.Person;
import com.departmentstore.api.domain.enums.PersonType;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    Person save(Person person);
    Optional<Person> findById(Long id);
    List<Person> findAllActive(int page, int size);
    List<Person> findByNameContaining(String name);
    List<Person> findByPersonType(PersonType personType);
    boolean existsById(Long id);
    void deleteById(Long id);
}
