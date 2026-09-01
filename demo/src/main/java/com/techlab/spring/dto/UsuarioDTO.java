package com.techlab.spring.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UsuarioDTO(
        @Schema(description = "ID unico del usuario generado al crear el pedido",example = "1")
        Integer id,
        @Schema(description = "Nombre del usuario asignado", example = "Pepe")
        String username,
        @Schema(description = "Correo electronico utilizado por el cliente", example = "pepethebest@gmail.com")
        String email
) {
}
