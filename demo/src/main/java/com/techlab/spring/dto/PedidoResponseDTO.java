package com.techlab.spring.dto;

import com.techlab.spring.entity.EstadoPedido;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(

        Integer id,
        LocalDateTime fecha,
        EstadoPedido estado,
        Double total,
        UsuarioDTO usuario,
        List<ProductoResponseDTO> productos
) {
}
