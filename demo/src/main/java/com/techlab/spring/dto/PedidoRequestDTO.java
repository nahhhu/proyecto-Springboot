package com.techlab.spring.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequestDTO {

    @NotNull(message = "El ID del usuario debe ser obligatorio")
    private Integer usuarioId;

    @NotEmpty(message = "La lista de productos no puede estar vacia")
    private List<Integer> productosId;
}
