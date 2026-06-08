package com.departmentstore.api.infrastructure.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Dados para cadastro de pessoa jurídica")
public record LegalPersonRequestDto(

        @Schema(description = "Nome da pessoa", example = "Empresa XYZ")
        @NotBlank(message = "Name is required")
        String name,

        @Schema(description = "CNPJ contendo 14 dígitos numéricos", example = "11444777000161")
        @Pattern(regexp = "^[0-9]{14}$", message = "CNPJ must contain 14 digits")
        String cnpj,

        @Schema(description = "Razão social", example = "Empresa XYZ Comércio LTDA")
        @NotBlank(message = "Corporate name is required")
        String corporateName,

        @Schema(description = "Capital social", example = "500000.00")
        @PositiveOrZero(message = "Share capital must be positive or zero")
        BigDecimal shareCapital,

        @Schema(description = "Quantidade de funcionários", example = "25")
        @PositiveOrZero(message = "Employees quantity must be positive or zero")
        Integer employeesQuant,

        @Schema(description = "Lista de responsáveis pela empresa")
        @NotEmpty(message = "Responsibles are required")
        @Valid
        List<ResponsibleRequestDto> responsaveis

) {}