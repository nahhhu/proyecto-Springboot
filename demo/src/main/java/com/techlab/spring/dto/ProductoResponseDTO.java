package com.techlab.spring.dto;

public record ProductoResponseDTO(
        Integer id,
        String nombre,
        Double precio,
        Integer stock,
        boolean activo,
        Integer categoriaId
) {
}
