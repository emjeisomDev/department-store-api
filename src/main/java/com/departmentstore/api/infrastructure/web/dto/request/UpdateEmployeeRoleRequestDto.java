package com.departmentstore.api.infrastructure.web.dto.request;

import com.departmentstore.api.domain.enums.EmployeeRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Atualização do cargo do funcionário")
public record UpdateEmployeeRoleRequestDto(

        @Schema(description = "Novo cargo do funcionário")
        @NotNull(message = "Employee role is required")
        EmployeeRole role

) {}
