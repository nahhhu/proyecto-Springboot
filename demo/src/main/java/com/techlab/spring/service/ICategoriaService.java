package com.techlab.spring.service;

import com.techlab.spring.dto.CategoriaRequestDTO;
import com.techlab.spring.dto.CategoriaResponseDTO;

import java.util.List;

public interface ICategoriaService {
    CategoriaResponseDTO crear(CategoriaRequestDTO categoriaRequestDTO);

    List<CategoriaResponseDTO> crearCategorias(List<CategoriaRequestDTO> categorias);

    List<CategoriaResponseDTO> listarCategorias();

    List<CategoriaResponseDTO> listarInactivas();

    CategoriaResponseDTO obtenerPorId(Integer id);

    CategoriaResponseDTO obtenerPorNombre(String nombre);

    CategoriaResponseDTO actualizar(Integer id, CategoriaRequestDTO categoriaRequestDTO);

    boolean desactivar(Integer id);

    boolean activar(Integer id);
}
