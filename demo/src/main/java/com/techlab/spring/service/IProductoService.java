package com.techlab.spring.service;

import com.techlab.spring.model.Producto;

import java.util.List;

public interface IProductoService {
    Producto crear(Producto producto);

    List<Producto> crearProductos(List<Producto> productos);

    List<Producto> listarProductos();

    Producto obtenerPorId(int id);

    List<Producto> obtenerPorNombre(String nombre);

    List<Producto> obtenerPorCategoria(String categoria);

    Producto actualizar(int id, Producto datos);

    Boolean eliminar(int id);
}
