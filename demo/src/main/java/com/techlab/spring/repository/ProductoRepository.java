package com.techlab.spring.repository;

import com.techlab.spring.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByNombreIgnoreCase(String nombre);
    List<Producto> fingByCategoria(String categoria);
}
