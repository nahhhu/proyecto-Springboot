package com.techlab.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductoDTO {
    private Integer id;

    @NotBlank(message = "Es necesario un nombre")
    private String nombre;

    @NotNull(message = "El precio es oblicatorio")
    @Positive(message = "El precio debe ser mayor a cero")
    private Double precio;

    @PositiveOrZero(message = "El stock no puede ser negativo")
    private Integer stock;

    @NotBlank(message = "Es necesario que se asigne categoria")
    private String categoria;
}
