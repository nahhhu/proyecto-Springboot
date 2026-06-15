package com.techlab.spring.mapper;

import com.techlab.spring.dto.ProductoRequestDTO;
import com.techlab.spring.dto.ProductoResponseDTO;
import com.techlab.spring.entity.Producto;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")

public interface ProductoMapper {

    List<ProductoResponseDTO> toDtoList(List<Producto> productos);

    List<Producto> toEntityList(List<ProductoRequestDTO> productosRequestDTO);

    @Mapping(source = "categoria.id", target = "categoriaId")
    ProductoResponseDTO toDto(Producto producto);

    @Mapping(target = "categoria", ignore = true)
    Producto toEntity(ProductoRequestDTO productoRequestDTO);

    @InheritConfiguration(name = "toEntity")
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ProductoResponseDTO dto, @MappingTarget Producto entity);
}
