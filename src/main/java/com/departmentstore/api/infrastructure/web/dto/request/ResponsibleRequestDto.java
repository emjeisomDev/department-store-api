package com.departmentstore.api.infrastructure.web.dto.request;

import jakarta.validation.constraints.*;

public record ResponsibleRequestDto(

        @NotNull(message = "Natural person id is required")
        Long naturalPersonId,

        @NotBlank(message = "Responsibility type is required")
        String responsibilityType
) {
}
