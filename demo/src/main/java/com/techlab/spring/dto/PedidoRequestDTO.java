package com.techlab.spring.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PedidoRequestDTO(
        @Schema(description = "Nombre del usuario", example = "Pepe")
        @NotBlank(message = "El nombre de usuario es obligatorio")
        String nombreUsuario,

        @Schema(description = "Email del usuario", example = "pepethebest@gmail.com")
        @NotBlank(message = "El email de usuario es obligatorio")
        @Email(message = "Debe tener un formato email valido")
        String emailUsuario,

        @Schema(description = "IDs de los productos que deben estar en el pedido" ,example = "1,3,22,59")
        @NotEmpty(message = "La lista de productos no puede estar vacia")
        List<Integer> productosId
) {
}