package com.departmentstore.api.infrastructure.web.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record LegalPersonResponseDto(
        Long id,
        String name,
        String cnpj,
        String corporateName,
        BigDecimal shareCapital,
        Integer employeesQuant,
        List<ResponsibleResponseDto> responsaveis,
        String status
) {
}
