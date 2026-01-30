package com.techlab.spring.mapper;


import com.techlab.spring.dto.PedidoRequestDTO;
import com.techlab.spring.dto.PedidoResponseDTO;
import com.techlab.spring.entity.Pedido;
import com.techlab.spring.entity.Producto;
import com.techlab.spring.entity.Usuario;
import com.techlab.spring.repository.ProductoRepository;
import com.techlab.spring.repository.UsuarioRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, ProductoMapper.class})
public abstract class PedidoMapper {

    @Autowired
    protected UsuarioRepository usuarioRepository;

    @Autowired
    protected ProductoRepository productoRepository;


    @Mapping(target = "usuario", source = "usuario")
    @Mapping(target = "productos", source = "productos")
    public abstract PedidoResponseDTO toResponseDto(Pedido pedido);

    public abstract List<PedidoResponseDTO> pedidoResponseDTOList(List<Pedido> pedidos);

    public Pedido toEntity(PedidoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Pedido pedido = new Pedido();

        Usuario usuario = usuarioRepository.findByEmail(dto.emailUsuario()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        pedido.setUsuario(usuario);

        List<Producto> productos = dto.productosId().stream().map(id -> productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto: " + id + " no encontrado"))).collect(Collectors.toList());
        pedido.setProductos(productos);

        return pedido;
    }
}
