package com.techlab.spring.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequestDTO(
        Integer id,
        @NotBlank(message = "La categoria debe tener un nombre")
        String nombre
) {
}
