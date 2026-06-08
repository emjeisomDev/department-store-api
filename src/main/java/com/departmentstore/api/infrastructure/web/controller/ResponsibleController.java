package com.departmentstore.api.infrastructure.web.controller;

import com.departmentstore.api.application.command.responsible.AddResponsibleCommand;
import com.departmentstore.api.application.port.in.ManageResponsibleUseCase;
import com.departmentstore.api.domain.entity.LegalPersonResponsible;
import com.departmentstore.api.infrastructure.web.dto.request.ResponsibleRequestDto;
import com.departmentstore.api.infrastructure.web.dto.response.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(
        name = "Responsáveis",
        description = "Gerenciamento de responsáveis por pessoa jurídica"
)
@RestController
@RequestMapping("/legal-persons/{legalPersonId}/responsibles")
public class ResponsibleController {

    private final ManageResponsibleUseCase useCase;

    public ResponsibleController(final ManageResponsibleUseCase useCase) {
        this.useCase = useCase;
    }

    @Operation(summary = "Listar responsáveis da pessoa jurídica")
    @GetMapping
    public ApiResponseDto<List<LegalPersonResponsible>> listResponsibles(
            @PathVariable final Long legalPersonId) {

        return new ApiResponseDto<>(
                true,
                useCase.listResponsibles(legalPersonId),
                "Responsibles retrieved successfully",
                LocalDateTime.now()
        );
    }

    @Operation(summary = "Adicionar responsável à pessoa jurídica")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Responsável vinculado")})
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

    @Operation(summary = "Encerrar responsabilidade")
    @DeleteMapping("/{rid}")
    public ApiResponseDto<Void> endResponsibility(@PathVariable final Long rid) {
        useCase.endResponsibility(rid);
        return new ApiResponseDto<>(
                true,
                null,
                "Responsibility ended successfully",
                LocalDateTime.now()
        );
    }
}