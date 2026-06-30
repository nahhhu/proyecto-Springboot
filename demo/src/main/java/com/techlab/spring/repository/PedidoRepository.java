package com.techlab.spring.repository;

import com.techlab.spring.entity.EstadoPedido;
import com.techlab.spring.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
List<Pedido> findByEstado(EstadoPedido estado);
}
