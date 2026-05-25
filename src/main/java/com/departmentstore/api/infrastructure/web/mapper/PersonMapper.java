package com.departmentstore.api.infrastructure.web.mapper;

import com.departmentstore.api.application.command.person.CreateLegalPersonCommand;
import com.departmentstore.api.application.command.person.CreateNaturalPersonCommand;
import com.departmentstore.api.application.command.responsible.ResponsibleCommand;
import com.departmentstore.api.domain.entity.LegalPerson;
import com.departmentstore.api.domain.entity.LegalPersonResponsible;
import com.departmentstore.api.domain.entity.NaturalPerson;
import com.departmentstore.api.domain.entity.Person;
import com.departmentstore.api.infrastructure.web.dto.request.LegalPersonRequestDto;
import com.departmentstore.api.infrastructure.web.dto.request.NaturalPersonRequestDto;
import com.departmentstore.api.infrastructure.web.dto.request.ResponsibleRequestDto;
import com.departmentstore.api.infrastructure.web.dto.response.LegalPersonResponseDto;
import com.departmentstore.api.infrastructure.web.dto.response.NaturalPersonResponseDto;
import com.departmentstore.api.infrastructure.web.dto.response.ResponsibleResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PersonMapper {

    public CreateNaturalPersonCommand toCommand(final NaturalPersonRequestDto dto) {
        return new CreateNaturalPersonCommand(
                dto.name(),
                dto.cpf(),
                dto.birthDate(),
                dto.mothersName(),
                dto.gender()
        );
    }

    public CreateLegalPersonCommand toCommand(final LegalPersonRequestDto dto) {
        return new CreateLegalPersonCommand(
                dto.name(),
                dto.cnpj(),
                dto.corporateName(),
                dto.shareCapital(),
                dto.employeesQuant(),
                dto.responsaveis()
                        .stream()
                        .map(this::toResponsibleCommand)
                        .toList()
        );
    }

    private ResponsibleCommand toResponsibleCommand(final ResponsibleRequestDto dto) {
        return new ResponsibleCommand(dto.naturalPersonId(), dto.responsibilityType());
    }

    public NaturalPersonResponseDto toResponseDto(final Person person, final NaturalPerson naturalPerson) {
        return new NaturalPersonResponseDto(
                naturalPerson.getId(),
                person.getName(),
                naturalPerson.getCpf().getFormatted(),
                naturalPerson.getBirthDate(),
                naturalPerson.getGender() != null
                        ? naturalPerson.getGender().name()
                        : null,
                person.isDeleted()
                        ? "EXCLUIDO"
                        : "ATIVO",
                person.getRegistrationDate()
        );
    }

    public LegalPersonResponseDto toResponseDto(
            final Person person,
            final LegalPerson legalPerson,
            final List<LegalPersonResponsible> responsibles)
    {
        return new LegalPersonResponseDto(
                legalPerson.getId(),
                person.getName(),
                legalPerson.getCnpj().getFormatted(),
                legalPerson.getCorporateName(),
                legalPerson.getShareCapital(),
                legalPerson.getEmployeesQuant(),
                responsibles.stream()
                        .map(this::toResponsibleResponseDto)
                        .toList(),
                person.isDeleted()
                        ? "EXCLUIDO"
                        : "ATIVO"
        );
    }

    private ResponsibleResponseDto toResponsibleResponseDto(final LegalPersonResponsible responsible) {
        return new ResponsibleResponseDto(
                responsible.getNaturalPersonId(),
                responsible.getResponsibilityType().name()
        );
    }
}