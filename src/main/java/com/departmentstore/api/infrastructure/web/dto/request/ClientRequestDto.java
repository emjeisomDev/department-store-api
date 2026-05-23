package com.departmentstore.api.infrastructure.web.dto.request;

import jakarta.validation.constraints.*;

public record ClientRequestDto(
        @NotNull(message = "Person id is required")
        Long personId,

        @NotBlank(message = "Client code is required")
        String clientCode,

        String clientRank
) {
}
