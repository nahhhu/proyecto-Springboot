package com.techlab.spring.service;

import com.techlab.spring.dto.PedidoRequestDTO;
import com.techlab.spring.dto.PedidoResponseDTO;

import java.util.List;

public interface IPedidoService {
    PedidoResponseDTO crearPedido(PedidoRequestDTO pedidoRequest);

    List<PedidoResponseDTO> listarPedidos();

        PedidoResponseDTO buscarPedido(Integer id);
}
