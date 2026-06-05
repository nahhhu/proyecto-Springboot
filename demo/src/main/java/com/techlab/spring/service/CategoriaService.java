package com.techlab.spring.service;

import com.techlab.spring.dto.CategoriaDTO;
import com.techlab.spring.dto.ProductoDTO;
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
    public List<CategoriaDTO> listarCategorias(){
        return mapper.toDtoList(repo.findAll());
    }

    @Override
    public CategoriaDTO obtenerPorId(Integer id){
        return repo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new CategoriaNotFoundException("La categoria con id: " + id + " no existe"));
    }
    @Override
    public CategoriaDTO obtenerPorNombre(String nombre){
        Categoria categoria = repo.findByNombreIgnoreCase(nombre)
                .orElseThrow(() -> new CategoriaNotFoundException("La categoria: " + nombre+ " no existe"));
        return mapper.toDto(categoria);
    }

    @Override
    public CategoriaDTO crear(CategoriaDTO categoriaDTO){
        if(repo.existByNombreIgnoreCase(categoriaDTO.nombre())){
            throw new CategoriaDuplicadaException("La categoria: " + categoriaDTO.nombre() + " ya existe");
        }
        Categoria categoria = mapper.toEntity(categoriaDTO);
        Categoria guardada = repo.save(categoria);
        return mapper.toDto(guardada);
    }
    @Override
    public List<CategoriaDTO> crearCategorias(List<CategoriaDTO> categoriaDTOS){
        for(CategoriaDTO c : categoriaDTOS){
            if(repo.existByNombreIgnoreCase(c.nombre())){
                throw new CategoriaDuplicadaException("El producto: " + c.nombre() + " ya existe");
            }
        }
        List<Categoria> entidades = mapper.toEntityList(categoriaDTOS);
        List<Categoria> guardados = repo.saveAll(entidades);
        return mapper.toDtoList(guardados);
    }

     @Override
    public CategoriaDTO actualizar(Integer id, CategoriaDTO categoriaDTO){
        Categoria existe = repo.findById(id).orElseThrow(() -> new CategoriaNotFoundException("No se puede actualizar. La categoria con id: " + id + " no existe."));

        mapper.updateEntityFromDto(categoriaDTO, existe);

        Categoria guardada =repo.save(existe);
        return mapper.toDto(guardada);
     }

     @Override
    public boolean desactivar(Integer id){
        Categoria categoria = repo.findById(id)
                .orElseThrow(() ->new CategoriaNotFoundException("La categoría con id:" + id + " no existe"));
       categoria.setActiva(false);
       repo.save(categoria);

       return true;
     }
}
