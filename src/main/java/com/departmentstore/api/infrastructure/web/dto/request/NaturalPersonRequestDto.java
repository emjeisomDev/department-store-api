package com.departmentstore.api.infrastructure.web.dto.request;

import com.departmentstore.api.domain.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "Dados para cadastro de pessoa física")
public record NaturalPersonRequestDto(

        @Schema(description = "Nome completo", example = "João da Silva")
        @NotBlank(message = "Name is required")
        String name,

        @Schema(description = "CPF contendo 11 dígitos numéricos", example = "52998224725")
        @Pattern(
                regexp = "^[0-9]{11}$",
                message = "CPF must contain 11 digits"
        )
        String cpf,

        @Schema(description = "Data de nascimento", example = "1990-05-20")
        @NotNull(message = "Birth date is required")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        @Schema(description = "Nome da mãe", example = "Maria da Silva")
        String mothersName,

        @Schema(description = "Gênero",
                allowableValues = {
                        "MALE",
                        "FEMALE",
                        "OTHER",
                        "NOT_INFORMED"
                }
        )
        @NotNull(message = "Gender is required")
        Gender gender

) {}
