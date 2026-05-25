package com.departmentstore.api.infrastructure.web.dto.request;

import com.departmentstore.api.domain.enums.ClientRank;
import jakarta.validation.constraints.NotNull;

public record UpdateClientRankRequestDto(
        @NotNull(message = "Client rank is required")
        ClientRank rank
) {
}
