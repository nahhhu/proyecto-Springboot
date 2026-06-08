package com.techlab.spring.service;

import com.techlab.spring.dto.ProductoRequestDTO;
import com.techlab.spring.dto.ProductoResponseDTO;

import java.util.List;

public interface IProductoService {
    ProductoResponseDTO crear(ProductoRequestDTO productoRequestDTO);

    List<ProductoResponseDTO> crearProductos(List<ProductoRequestDTO> productoRequestDTOList);

    List<ProductoResponseDTO> listarProductos();

    ProductoResponseDTO obtenerPorId(Integer id);

    List<ProductoResponseDTO> obtenerPorNombre(String nombre);

    List<ProductoResponseDTO> obtenerPorCategoria(Integer categoriaId);

    ProductoResponseDTO actualizar(Integer id, ProductoResponseDTO datosDto);

    boolean desactivar (Integer id);

    boolean activar (Integer id);
}
