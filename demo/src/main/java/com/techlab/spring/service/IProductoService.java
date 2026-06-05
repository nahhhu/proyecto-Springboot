package com.techlab.spring.service;

import com.techlab.spring.dto.ProductoDTO;

import java.util.List;

public interface IProductoService {
    ProductoDTO crear(ProductoDTO productoDTO);

    List<ProductoDTO> crearProductos(List<ProductoDTO> productosDTO);

    List<ProductoDTO> listarProductos();

    ProductoDTO obtenerPorId(Integer id);

    List<ProductoDTO> obtenerPorNombre(String nombre);

    List<ProductoDTO> obtenerPorCategoria(Integer categoriaId);

    ProductoDTO actualizar(Integer id, ProductoDTO datosDto);

    Boolean eliminar(Integer id);
}
