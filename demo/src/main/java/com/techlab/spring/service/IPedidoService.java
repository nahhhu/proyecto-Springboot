package com.techlab.spring.service;

import com.techlab.spring.dto.PedidoRequestDTO;
import com.techlab.spring.dto.PedidoResponseDTO;
import com.techlab.spring.entity.Pedido;

import java.util.List;

public interface IPedidoService {
    PedidoResponseDTO crearPedido(PedidoRequestDTO pedidoRequest);

    List<PedidoResponseDTO> listarPedidos();

    PedidoRequestDTO buscarPedido( int id);
}
