package com.techlab.spring.dto;

import jakarta.validation.constraints.*;

public record ProductoRequestDTO(
        @NotBlank(message = "El producto necesita un nombre")
        String nombre,

        @NotBlank(message = "La descripcion es obligatoria")
        String descripcion,

        @NotNull(message = "El precio es obligatorio")
        @Positive(message = "El precio debe ser mayor a 0")
        Double precio,

        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock,

        @NotNull(message = "La categoria es obligatoria")
        Integer categoriaId
) {
}
