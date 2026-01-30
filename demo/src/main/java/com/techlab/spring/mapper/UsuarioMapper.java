package com.techlab.spring.mapper;

import com.techlab.spring.dto.UsuarioDTO;
import com.techlab.spring.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {


    UsuarioDTO toDto(Usuario usuario);

    Usuario toEntity(UsuarioDTO usuarioDTO);

}
