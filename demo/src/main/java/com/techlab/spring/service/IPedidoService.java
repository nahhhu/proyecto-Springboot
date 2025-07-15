package com.techlab.spring.service;

import com.techlab.spring.model.Pedido;

import java.util.List;

public interface IPedidoService {
    Pedido crearPedido(Pedido pedido);
    List<Pedido> listarPedidos();
    Pedido buscarPedido(int id);
}
