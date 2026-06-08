package com.techlab.spring.repository;

import com.techlab.spring.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT p FROM Producto p JOIN FETCH p.categoria")
    List<Producto> buscarTodosPorCategoria();

    List<Producto> findByCategoriaId(Integer categoriaId);

    boolean existsByNombreIgnoreCase(String nombre);

    List<Producto> findByActivoTrue();
}
