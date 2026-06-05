package com.techlab.spring.repository;

import com.techlab.spring.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByNombreIgnoreCase(String nombre);

    @Query("SELECT p FROM Producto p JOIN FETCH p.categoria")
    List<Producto> findByCategoria(String categoria);

    boolean existsByNombreIgnoreCase(String nombre);
}
