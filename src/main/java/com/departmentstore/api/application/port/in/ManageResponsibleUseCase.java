package com.departmentstore.api.application.port.in;

import com.departmentstore.api.application.command.responsible.AddResponsibleCommand;
import com.departmentstore.api.domain.entity.LegalPersonResponsible;

import java.util.List;

public interface ManageResponsibleUseCase {
    LegalPersonResponsible addResponsible(AddResponsibleCommand command);
    List<LegalPersonResponsible> listResponsibles( Long legalPersonId);
    void endResponsibility( Long responsibilityId);
}
