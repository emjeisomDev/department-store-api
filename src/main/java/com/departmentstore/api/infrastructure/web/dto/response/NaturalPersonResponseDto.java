package com.departmentstore.api.infrastructure.web.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record NaturalPersonResponseDto(
        Long id,
        String name,
        String cpf,
        LocalDate birthDate,
        String gender,
        String status,
        LocalDateTime createdAt
) {
}
