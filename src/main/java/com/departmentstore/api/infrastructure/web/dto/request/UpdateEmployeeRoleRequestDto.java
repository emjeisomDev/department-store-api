package com.departmentstore.api.infrastructure.web.dto.request;

import com.departmentstore.api.domain.enums.EmployeeRole;
import jakarta.validation.constraints.NotNull;

public record UpdateEmployeeRoleRequestDto(
        @NotNull(message = "Employee role is required")
        EmployeeRole role
) {
}
