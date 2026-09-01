package com.techlab.spring.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ProductoResponseDTO(
        @Schema(description = "ID unico del producto en ld BDD")
        Integer id,
        @Schema(description = "Nombre comercial del producto")
        String nombre,
        @Schema(description = "Precio final de venta")
        Double precio,
        @Schema(description = "Cantidad de stock fisica disponible")
        Integer cantidadStock,
        @Schema(description = "Indica si el producto esta disponible para la venta")
        boolean activo,
        @Schema(description = "ID de la categoria a la que pertenece")
        Integer categoriaId,
        @Schema(description = "Lista de imagenes asociadas al producto")
        List<ImagenResponseDTO> imagenes
) {
}
