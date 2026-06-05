package com.techlab.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductoDTO(
        Integer id,

        @NotBlank(message = "Es necesario un nombre")
        String nombre,

        @NotNull(message = "El precio es oblicatorio")
        @Positive(message = "El precio debe ser mayor a cero")
        Double precio,

        @PositiveOrZero(message = "El stock no puede ser negativo")
        Integer stock,

        @NotBlank(message = "Es necesario que se asigne categoria")
        Integer categoriaId
) {
}
