package com.departmentstore.api.infrastructure.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Responsável vinculado a uma pessoa jurídica")
public record ResponsibleRequestDto(

        @Schema(description = "ID da pessoa física responsável", example = "1")
        @NotNull(message = "Natural person id is required")
        Long naturalPersonId,

        @Schema(description = "Tipo de responsabilidade", example = "ADMINISTRATOR")
        @NotBlank(message = "Responsibility type is required")
        String responsibilityType

) {}
