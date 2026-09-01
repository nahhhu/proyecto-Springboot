package com.techlab.spring.dto;

import com.techlab.spring.entity.EstadoPedido;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
        @Schema(description = "ID unico del pedido en la BDD", example = "1")
        Integer id,
        @Schema(description = "Fecha exacta en la que se creo el pedido", example = "2026-07-01 23:14")
        LocalDateTime fecha,
        @Schema(description = "Indica el estado en el que se encuentra el pedido",example = "Pendiente")
        EstadoPedido estado,
        @Schema(description = "Precio final del pedido", example = "130000.40")
        Double total,
        @Schema(description = "Datos del usuario que creo el pedido")
        UsuarioDTO usuario,
        @Schema(description = "Lista con los IDs de los productos seleccionados", example = "1,4,6.21")
        List<ProductoResponseDTO> productos
) {
}
