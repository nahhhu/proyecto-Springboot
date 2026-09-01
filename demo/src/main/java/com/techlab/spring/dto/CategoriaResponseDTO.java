package com.techlab.spring.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con datos de una categoria")
public record CategoriaResponseDTO(
        @Schema(description = "ID unico de la categoria", example = "1")
        Integer id,
        @Schema(description = "Nombre de la categoria", example = "Accesorios")
        String nombre,
        @Schema(description = "Indica si la categoria esta activa")
        boolean activa
) {
}
