package com.techlab.spring.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PedidoRequestDTO(
        @NotBlank(message = "El nombre de usuario es obligatorio")
        String nombreUsuario,

        @NotBlank(message = "El email de usuario es obligatorio")
        @Email(message = "Debe tener un formato email valido")
        String emailUsuario,

        @NotEmpty(message = "La lista de productos no puede estar vacia")
        List<Integer> productosId
) {
}