package com.departmentstore.api.infrastructure.web.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record ErrorResponseDto(
        String code,
        String message,
        Map<String, Object> details,
        LocalDateTime timestamp
) {
}
