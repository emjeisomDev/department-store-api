package com.departmentstore.api.infrastructure.web.dto.request;

import com.departmentstore.api.domain.enums.ClientRank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Atualização da classificação do cliente")
public record UpdateClientRankRequestDto(

        @Schema(description = "Nova classificação do cliente")
        @NotNull(message = "Client rank is required")
        ClientRank rank

) {}
