package com.departmentstore.api.application.service;

import com.departmentstore.api.application.command.responsible.AddResponsibleCommand;
import com.departmentstore.api.application.port.in.ManageResponsibleUseCase;
import com.departmentstore.api.domain.entity.LegalPerson;
import com.departmentstore.api.domain.entity.LegalPersonResponsible;
import com.departmentstore.api.domain.entity.NaturalPerson;
import com.departmentstore.api.domain.enums.ResponsibilityType;
import com.departmentstore.api.domain.exception.ResponsibleNotFoundException;
import com.departmentstore.api.domain.repository.LegalPersonRepository;
import com.departmentstore.api.domain.repository.LegalPersonResponsibleRepository;
import com.departmentstore.api.domain.repository.NaturalPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ResponsibleService implements ManageResponsibleUseCase {

    private final LegalPersonResponsibleRepository legalPersonResponsibleRepository;
    private final LegalPersonRepository legalPersonRepository;
    private final NaturalPersonRepository naturalPersonRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LegalPersonResponsible> listResponsibles(final Long legalPersonId) {
        return legalPersonResponsibleRepository
                .findActiveByLegalPersonId(legalPersonId);
    }

    @Override
    public LegalPersonResponsible addResponsible(final AddResponsibleCommand command) {

        LegalPerson legalPerson =
                legalPersonRepository
                        .findById(command.legalPersonId())
                        .orElseThrow(() -> new IllegalArgumentException("LegalPerson not found. Id=" + command.legalPersonId()));

        NaturalPerson naturalPerson =
                naturalPersonRepository
                        .findById(command.naturalPersonId())
                        .orElseThrow(() -> new IllegalArgumentException("NaturalPerson not found. Id=" + command.naturalPersonId()));

        LegalPersonResponsible responsible =
                new LegalPersonResponsible(
                        null,
                        legalPerson.getId(),
                        naturalPerson.getId(),
                        ResponsibilityType.valueOf(
                                command.responsibilityType()
                        ),
                        LocalDate.now()
                );

        return legalPersonResponsibleRepository
                .save(responsible);
    }

    @Override
    public void endResponsibility(final Long responsibilityId) {

        LegalPersonResponsible responsible =
                legalPersonResponsibleRepository
                        .findById(responsibilityId)
                        .orElseThrow(() -> new ResponsibleNotFoundException(responsibilityId));

        responsible.finish(LocalDate.now());

        legalPersonResponsibleRepository.save(responsible);
    }

}
