package com.techlab.spring.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos para crear o actualizar una categoria")
public record CategoriaRequestDTO(
        @Schema(description = "ID de la categoria (solo para actualizaciones)", example = "1")
        Integer id,
        @Schema(description = "Nombre de la categoria", example = "Accesorios")
        @NotBlank(message = "La categoria debe tener un nombre")
        String nombre
) {
}
