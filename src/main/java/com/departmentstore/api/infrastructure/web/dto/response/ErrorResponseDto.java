package com.departmentstore.api.infrastructure.web.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDto(
        String code,
        String message,
        List<String> details,
        LocalDateTime timestamp
) {
}
