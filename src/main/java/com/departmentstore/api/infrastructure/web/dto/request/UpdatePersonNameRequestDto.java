package com.departmentstore.api.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdatePersonNameRequestDto(
        @NotBlank(message = "New name is required")
        String newName
) {
}
