package com.departmentstore.api.application.port.in;

import com.departmentstore.api.application.command.person.CreateLegalPersonCommand;
import com.departmentstore.api.application.command.person.CreateNaturalPersonCommand;
import com.departmentstore.api.domain.entity.LegalPerson;
import com.departmentstore.api.domain.entity.NaturalPerson;

public interface CreatePersonUseCase {
    NaturalPerson createNaturalPerson(CreateNaturalPersonCommand command);
    LegalPerson createLegalPerson(CreateLegalPersonCommand command);
}
