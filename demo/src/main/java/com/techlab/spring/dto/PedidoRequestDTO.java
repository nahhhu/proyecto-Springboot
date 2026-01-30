package com.techlab.spring.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoRequestDTO(
        @NotNull(message = "El ID del usuario debe ser obligartorio")
        String nombreUsuario,
        String emailUsuario,

        @NotEmpty(message = "La lista de productos no puede estar vacis")
        List<Integer> productosId
) {
}