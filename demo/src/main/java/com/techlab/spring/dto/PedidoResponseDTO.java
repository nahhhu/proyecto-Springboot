package com.techlab.spring.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(

        Integer id,
        LocalDateTime fecha,
        String estado,
        Double total,
        UsuarioDTO usuario,
        List<ProductoDTO> productos
) {
}
