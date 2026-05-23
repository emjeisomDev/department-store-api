package com.departmentstore.api.infrastructure.web.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record NaturalPersonRequestDto(
        @NotBlank(message = "Name is required")
        String name,

        @Pattern(
                regexp = "^[0-9]{11}$",
                message = "CPF must contain 11 digits"
        )
        String cpf,

        @NotNull(message = "Birth date is required")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        String mothersName,

        @Pattern(
                regexp = "^[FMON]$",
                message = "Gender must be F, M, O or N"
        )
        String gender
) {
}
