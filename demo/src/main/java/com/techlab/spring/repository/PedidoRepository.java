package com.techlab.spring.repository;

import com.techlab.spring.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository  extends JpaRepository<Pedido, Integer> {

}
