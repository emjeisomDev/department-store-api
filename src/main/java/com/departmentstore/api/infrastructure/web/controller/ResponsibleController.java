package com.departmentstore.api.infrastructure.web.controller;

import com.departmentstore.api.application.command.responsible.AddResponsibleCommand;
import com.departmentstore.api.application.port.in.ManageResponsibleUseCase;
import com.departmentstore.api.domain.entity.LegalPersonResponsible;
import com.departmentstore.api.infrastructure.web.dto.request.ResponsibleRequestDto;
import com.departmentstore.api.infrastructure.web.dto.response.ApiResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/legal-persons/{legalPersonId}/responsibles")
public class ResponsibleController {

    private final ManageResponsibleUseCase useCase;

    public ResponsibleController(final ManageResponsibleUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<Long> addResponsible(
            @PathVariable final Long legalPersonId,
            @Valid @RequestBody final ResponsibleRequestDto request
    ) {

        LegalPersonResponsible responsible =
                useCase.addResponsible(
                        new AddResponsibleCommand(
                                legalPersonId,
                                request.naturalPersonId(),
                                request.responsibilityType()
                        )
                );

        return new ApiResponseDto<>(
                true,
                responsible.getId(),
                "Responsible added successfully",
                LocalDateTime.now()
        );
    }
}