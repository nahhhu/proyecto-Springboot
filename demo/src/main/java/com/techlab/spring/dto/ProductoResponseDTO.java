package com.techlab.spring.dto;

import java.util.List;

public record ProductoResponseDTO(
        Integer id,
        String nombre,
        Double precio,
        Integer cantidadStock,
        boolean activo,
        Integer categoriaId,
        List<ImagenResponseDTO> imagenes
) {
}
