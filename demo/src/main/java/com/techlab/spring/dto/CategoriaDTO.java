package com.techlab.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoriaDTO(
        Integer id,
        @NotBlank(message = "La categoria debe tener un nombre")
        String nombre,
        boolean activa
){
}
