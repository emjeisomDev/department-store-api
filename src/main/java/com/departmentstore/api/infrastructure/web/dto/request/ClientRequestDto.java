package com.departmentstore.api.infrastructure.web.dto.request;

import com.departmentstore.api.domain.enums.ClientRank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Dados para cadastro de cliente")
public record ClientRequestDto(
        @Schema(description = "ID da pessoa vinculada ao cliente", example = "1")
        @NotNull(message = "Person id is required")
        Long personId,

        @Schema(description = "Código interno do cliente", example = "CLI-000001")
        @NotBlank(message = "Client code is required")
        String clientCode,

        @Schema(
                description = "Classificação do cliente",
                example = "STANDARD",
                allowableValues = {
                        "STANDARD"
                }
        )
        ClientRank clientRank
) {
}
