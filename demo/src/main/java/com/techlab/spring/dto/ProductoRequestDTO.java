package com.techlab.spring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductoRequestDTO(
        @Schema(description = "Nombre del producto", example="Teclado Redragon Kumara")
        @NotBlank(message = "El producto necesita un nombre")
        String nombre,

        @Schema(description = "Descripcion del producto", example = "Color: Blanco - Tipo: Mecanico")
        @NotBlank(message = "La descripcion es obligatoria")
        String descripcion,

        @Schema(description = "Precio obligatorio del producto", example = "100000.50")
        @NotNull(message = "El precio es obligatorio")
        @Positive(message = "El precio debe ser mayor a 0")
        Double precio,

        @Schema(description = "Cantidad de unidades fisicos disponibles", example = "10")
        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer cantidadStock,

        @Schema(description = "ID de la categoria a la que pertenece", example = "1")
        @NotNull(message = "La categoria es obligatoria")
        Integer categoriaId
) {
}
