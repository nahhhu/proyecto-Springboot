package com.techlab.spring.mapper;

import com.techlab.spring.dto.CategoriaDTO;
import com.techlab.spring.entity.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    CategoriaDTO toDto(Categoria categoria);

    Categoria toEntity(CategoriaDTO categoriaDTO);

    List<Categoria> toEntityList(List<CategoriaDTO> categoriaDTOS);

    List<CategoriaDTO> toDtoList(List<Categoria> categorias);

    void updateEntityFromDto(CategoriaDTO categoriaDto, @MappingTarget Categoria categoria);
}
