package com.departmentstore.api.application.port.in;

import com.departmentstore.api.application.command.person.SoftDeletePersonCommand;
import com.departmentstore.api.application.command.person.UpdatePersonNameCommand;
import com.departmentstore.api.domain.entity.Person;

import java.util.List;
import java.util.Optional;

public interface ManagePersonUseCase {
    void updateName(UpdatePersonNameCommand command);
    void softDelete(SoftDeletePersonCommand command);
    void restore(Long personId);
    Optional<Person> findById(Long personId);
    List<Person> findAllActive(int page, int size);
}
