package com.techlab.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDTO {

    private Integer id;
    private LocalDateTime fecha;
    private String estado;
    private Double total;

    private UsuarioDTO usuario;

    private List<ProductoDTO> productos;


}
