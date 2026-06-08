package com.departmentstore.api.infrastructure.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados do responsável vinculado à pessoa jurídica")
public record ResponsibleResponseDto(

        @Schema(description = "Identificador da pessoa física responsável", example = "3")
        Long naturalPersonId,

        @Schema(description = "Tipo de responsabilidade", example = "ADMINISTRATOR")
        String responsibilityType

) {
}
