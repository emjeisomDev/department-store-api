package com.departmentstore.api.infrastructure.web.dto.response;

public record ClientResponseDto(
        Long id,
        String personName,
        String clientCode,
        String rank,
        String status
) {
}
