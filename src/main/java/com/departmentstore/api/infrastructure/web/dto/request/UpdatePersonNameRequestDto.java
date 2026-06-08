package com.departmentstore.api.infrastructure.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Atualização do nome da pessoa")
public record UpdatePersonNameRequestDto(

        @Schema(description = "Novo nome da pessoa", example = "João Carlos da Silva")
        @NotBlank(message = "New name is required")
        String newName
) {}
