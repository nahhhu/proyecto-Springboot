package com.techlab.spring.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ImagenResponseDTO(
        @Schema(description = "ID unico de la imagen seleccionada para representar el producto", example = "1")
        Integer id,
        @Schema(description = "URL de la imagen")
        String url,
        @Schema(description = "Descripcion de la imagen")
        String descripcion,
        @Schema(description = "ID del producto al que esta enlazada la imagen")
        Integer productoId
)
{}
