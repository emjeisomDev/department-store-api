package com.departmentstore.api.infrastructure.web.dto.response;

import java.time.LocalDateTime;

public record ApiResponseDto<T>(
        boolean success,
        T data,
        String message,
        LocalDateTime timestamp
) {
}
