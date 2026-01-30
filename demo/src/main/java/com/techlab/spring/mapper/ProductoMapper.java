package com.techlab.spring.mapper;

import com.techlab.spring.dto.ProductoDTO;
import com.techlab.spring.entity.Producto;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")

public interface ProductoMapper {

    List<ProductoDTO> toDtoList(List<Producto> productos);

    List<Producto> toEntityList(List<ProductoDTO> productoDTOS);

    @Mapping(source = "cantidadStock", target = "stock")
    ProductoDTO toDto(Producto producto);

    @Mapping(source = "stock", target = "cantidadStock")
    Producto toEntity(ProductoDTO productoDTO);

    @InheritConfiguration(name = "toEntity")
    void updateEntityFromDto(ProductoDTO dto, @MappingTarget Producto entity);
}
