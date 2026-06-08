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

    @Mapping(source = "cantidadStock", target = "stock")
    ProductoResponseDTO toDto(Producto producto);

    @Mapping(source = "stock", target = "cantidadStock")
    Producto toEntity(ProductoRequestDTO productoRequestDTO);

    @InheritConfiguration(name = "toEntity")
    void updateEntityFromDto(ProductoResponseDTO dto, @MappingTarget Producto entity);
}
