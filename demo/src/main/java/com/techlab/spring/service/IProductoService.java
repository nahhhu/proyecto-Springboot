package com.techlab.spring.service;

import com.techlab.spring.dto.ProductoDTO;
import com.techlab.spring.entity.Producto;

import java.util.List;

public interface IProductoService {
    ProductoDTO crear(ProductoDTO productoDTO);

    List<ProductoDTO> crearProductos(List<ProductoDTO> productosDTO);

    List<ProductoDTO> listarProductos();

    ProductoDTO obtenerPorId(int id);

    List<ProductoDTO> obtenerPorNombre(String nombre);

    List<ProductoDTO> obtenerPorCategoria(String categoria);

    ProductoDTO actualizar(int id, ProductoDTO datosDto);

    Boolean eliminar(int id);
}
