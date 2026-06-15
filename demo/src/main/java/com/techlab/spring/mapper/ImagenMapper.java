package com.techlab.spring.mapper;

import com.techlab.spring.dto.ImagenResponseDTO;
import com.techlab.spring.entity.Imagen;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImagenMapper {

    @Mapping(source = "producto.id", target = "productoId")
    ImagenResponseDTO toDto(Imagen imagen);
}
