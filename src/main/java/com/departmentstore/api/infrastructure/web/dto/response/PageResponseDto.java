package com.departmentstore.api.infrastructure.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Resposta paginada")
public record PageResponseDto<T>(

        @Schema(description = "Conteúdo da página")
        List<T> content,

        @Schema(description = "Número da página atual", example = "0")
        int page,

        @Schema(description = "Quantidade de registros por página", example = "20")
        int size,

        @Schema(description = "Total de registros encontrados", example = "125")
        long totalElements,

        @Schema(description = "Total de páginas", example = "7")
        int totalPages

) {
}