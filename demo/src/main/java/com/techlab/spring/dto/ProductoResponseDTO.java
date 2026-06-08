package com.techlab.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductoResponseDTO(
        Integer id,
        String nombre,
        Double precio,
        Integer stock,
        boolean activo,
        Integer categoriaId
) {
}
