package com.departmentstore.api.infrastructure.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Dados de retorno de pessoa jurídica")
public record LegalPersonResponseDto(

        @Schema(description = "Identificador da pessoa jurídica", example = "5")
        Long id,

        @Schema(description = "Nome da pessoa", example = "Empresa XYZ")
        String name,

        @Schema(description = "CNPJ", example = "11444777000161")
        String cnpj,

        @Schema(description = "Razão social", example = "Empresa XYZ Comércio LTDA")
        String corporateName,

        @Schema(description = "Capital social", example = "1000000.00")
        BigDecimal shareCapital,

        @Schema(description = "Quantidade de funcionários", example = "50")
        Integer employeesQuant,

        @Schema(description = "Lista de responsáveis")
        List<ResponsibleResponseDto> responsaveis,

        @Schema(description = "Status do registro", example = "ACTIVE")
        String status

) {
}
