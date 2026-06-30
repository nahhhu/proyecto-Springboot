package com.techlab.spring.mapper;

import com.techlab.spring.dto.CategoriaRequestDTO;
import com.techlab.spring.dto.CategoriaResponseDTO;
import com.techlab.spring.entity.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    CategoriaResponseDTO toDto(Categoria categoria);

    Categoria toEntity(CategoriaRequestDTO categoriaRequestDTO);

    List<Categoria> toEntityList(List<CategoriaRequestDTO> categoriaRequestDTOS);

    List<CategoriaResponseDTO> toDtoList(List<Categoria> categorias);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(CategoriaRequestDTO categoriaRequestDto, @MappingTarget Categoria categoria);
}
