package com.techlab.spring.service;

import com.techlab.spring.dto.CategoriaRequestDTO;
import com.techlab.spring.dto.CategoriaResponseDTO;
import com.techlab.spring.entity.Categoria;
import com.techlab.spring.exception.CategoriaDuplicadaException;
import com.techlab.spring.exception.CategoriaNotFoundException;
import com.techlab.spring.mapper.CategoriaMapper;
import com.techlab.spring.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService implements ICategoriaService {

    private final CategoriaRepository repo;
    private final CategoriaMapper mapper;

    @Override
    public List<CategoriaResponseDTO> listarCategorias() {
        return mapper.toDtoList(repo.findAll());
    }

    @Override
    public CategoriaResponseDTO obtenerPorId(Integer id) {
        return repo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new CategoriaNotFoundException("La categoria con id: " + id + " no existe"));
    }

    @Override
    public CategoriaResponseDTO obtenerPorNombre(String nombre) {
        Categoria categoria = repo.findByNombreIgnoreCase(nombre)
                .orElseThrow(() -> new CategoriaNotFoundException("La categoria: " + nombre + " no existe"));
        return mapper.toDto(categoria);
    }

    @Override
    public CategoriaResponseDTO crear(CategoriaRequestDTO categoriaRequestDTO) {
        if (repo.existsByNombreIgnoreCase(categoriaRequestDTO.nombre())) {
            throw new CategoriaDuplicadaException("La categoria: " + categoriaRequestDTO.nombre() + " ya existe");
        }

        Categoria categoria = mapper.toEntity(categoriaRequestDTO);
        categoria.setActiva(true);
        Categoria guardada = repo.save(categoria);
        return mapper.toDto(guardada);
    }

    @Override
    public List<CategoriaResponseDTO> crearCategorias(List<CategoriaRequestDTO> categoriaRequestDTOS) {
        for (CategoriaRequestDTO c : categoriaRequestDTOS) {
            if (repo.existsByNombreIgnoreCase(c.nombre())) {
                throw new CategoriaDuplicadaException("El producto: " + c.nombre() + " ya existe");
            }
        }
        List<Categoria> entidades = mapper.toEntityList(categoriaRequestDTOS);
        List<Categoria> guardados = repo.saveAll(entidades);
        return mapper.toDtoList(guardados);
    }

    @Override
    public CategoriaResponseDTO actualizar(Integer id, CategoriaRequestDTO categoriaRequestDTO) {
        Categoria existe = repo.findById(id).orElseThrow(() -> new CategoriaNotFoundException("No se puede actualizar. La categoria con id: " + id + " no existe."));

        mapper.updateEntityFromDto(categoriaRequestDTO, existe);

        Categoria guardada = repo.save(existe);
        return mapper.toDto(guardada);
    }

    @Override
    public boolean desactivar(Integer id) {
        Categoria categoria = repo.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("La categoría con id:" + id + " no existe"));
        categoria.setActiva(false);
        repo.save(categoria);

        return true;
    }

    public boolean activar(Integer id){
        Categoria categoria = repo.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("La categoria con id: " + id + " no existe"));
        categoria.setActiva(true);
        repo.save(categoria);
        return true;
    }
}
