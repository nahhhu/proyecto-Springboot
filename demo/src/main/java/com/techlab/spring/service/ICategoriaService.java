package com.techlab.spring.service;

import com.techlab.spring.dto.CategoriaDTO;
import java.util.List;

public interface ICategoriaService {
    CategoriaDTO crear(CategoriaDTO categoriaDTO);

    List<CategoriaDTO> crearCategorias(List<CategoriaDTO> categorias);

    List<CategoriaDTO> listarCategorias();

    CategoriaDTO obtenerPorId(Integer id);

    CategoriaDTO obtenerPorNombre(String nombre);

    CategoriaDTO actualizar(Integer id, CategoriaDTO categoriaDTO);

    boolean desactivar( Integer id);
}
