package com.departmentstore.api.infrastructure.web.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record LegalPersonRequestDto(
        @NotBlank(message = "Name is required")
        String name,

        @Pattern(regexp = "^[0-9]{14}$", message = "CNPJ must contain 14 digits")
        String cnpj,

        @NotBlank(message = "Corporate name is required")
        String corporateName,

        @PositiveOrZero(message = "Share capital must be positive or zero")
        BigDecimal shareCapital,

        @PositiveOrZero(message = "Employees quantity must be positive or zero")
        Integer employeesQuant,

        @NotEmpty(message = "Responsibles are required")
        @Valid
        List<ResponsibleRequestDto> responsaveis
) {
}
