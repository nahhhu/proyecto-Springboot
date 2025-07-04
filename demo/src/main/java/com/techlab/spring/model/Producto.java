package com.techlab.spring.model;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Es necesario un nombre")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que cero")
    private Double precio;

    @PositiveOrZero(message = "El stock no puede ser negativo")
    private Integer cantidadStock;

    @NotBlank(message = "Es necesario que se asigne categoria")
    private String categoria;
}
