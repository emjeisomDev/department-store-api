package com.departmentstore.api.infrastructure.web.controller;

import com.departmentstore.api.application.port.in.CreatePersonUseCase;
import com.departmentstore.api.application.port.in.ManagePersonUseCase;
import com.departmentstore.api.domain.entity.LegalPerson;
import com.departmentstore.api.domain.entity.NaturalPerson;
import com.departmentstore.api.domain.entity.Person;
import com.departmentstore.api.infrastructure.web.dto.request.LegalPersonRequestDto;
import com.departmentstore.api.infrastructure.web.dto.request.NaturalPersonRequestDto;
import com.departmentstore.api.infrastructure.web.dto.response.ApiResponseDto;
import com.departmentstore.api.infrastructure.web.dto.response.LegalPersonResponseDto;
import com.departmentstore.api.infrastructure.web.dto.response.NaturalPersonResponseDto;
import com.departmentstore.api.infrastructure.web.mapper.PersonMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {
    private final CreatePersonUseCase createPersonUseCase;
    private final ManagePersonUseCase managePersonUseCase;
    private final PersonMapper mapper;

    public PersonController(
            final CreatePersonUseCase createPersonUseCase,
            final ManagePersonUseCase managePersonUseCase,
            final PersonMapper mapper
    ) {
        this.createPersonUseCase = createPersonUseCase;
        this.managePersonUseCase = managePersonUseCase;
        this.mapper = mapper;
    }

    @PostMapping("/natural")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<NaturalPersonResponseDto> createNaturalPerson(
            @Valid @RequestBody final NaturalPersonRequestDto request) {

        NaturalPerson naturalPerson = createPersonUseCase.createNaturalPerson(mapper.toCommand(request));
        Person person = managePersonUseCase.findById(naturalPerson.getPersonId()).orElseThrow();

        return new ApiResponseDto<>(
                true,
                mapper.toResponseDto(person, naturalPerson),
                "Natural person created successfully",
                LocalDateTime.now()
        );
    }

    @PostMapping("/legal")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<LegalPersonResponseDto> createLegalPerson(
            @Valid @RequestBody final LegalPersonRequestDto request
    ) {
        LegalPerson legalPerson = createPersonUseCase.createLegalPerson(mapper.toCommand(request));
        Person person = managePersonUseCase.findById(legalPerson.getPersonId()).orElseThrow();
        return new ApiResponseDto<>(
                true,
                mapper.toResponseDto(person, legalPerson,List.of()),
                "Legal person created successfully",
                LocalDateTime.now()
        );
    }

    @DeleteMapping("/{personId}")
    public ApiResponseDto<Void> softDelete(@PathVariable final Long personId) {

        managePersonUseCase.softDelete(
                new com.departmentstore.api.application.command.person
                        .SoftDeletePersonCommand(personId, "SYSTEM"));

        return new ApiResponseDto<>(
                true,
                null,
                "Person deleted successfully",
                LocalDateTime.now()
        );
    }

    @PatchMapping("/{personId}/restore")
    public ApiResponseDto<Void> restore(@PathVariable final Long personId) {
        managePersonUseCase.restore(personId);

        return new ApiResponseDto<>(
                true,
                null,
                "Person restored successfully",
                LocalDateTime.now()
        );
    }


}
