package com.techlab.spring.service;

import com.techlab.spring.dto.PedidoRequestDTO;
import com.techlab.spring.dto.PedidoResponseDTO;
import com.techlab.spring.entity.Pedido;
import com.techlab.spring.entity.Producto;
import com.techlab.spring.entity.Usuario;
import com.techlab.spring.exception.CrearPedidoException;
import com.techlab.spring.exception.PedidoNotFoundException;
import com.techlab.spring.mapper.PedidoMapper;
import com.techlab.spring.repository.PedidoRepository;
import com.techlab.spring.repository.ProductoRepository;
import com.techlab.spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService implements IPedidoService {

    @Autowired
    private final PedidoRepository repo;
    @Autowired
    public PedidoMapper pedidoMapper;
    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private ProductoRepository productoRepo;

    public PedidoService(PedidoRepository repo) {
        this.repo = repo;
    }

    @Override
    public PedidoResponseDTO crearPedido(PedidoRequestDTO pedidoRequest) {
        //En caso de que el usuario no exista, lo crea
        Usuario usuario = usuarioRepo.findByEmail(pedidoRequest.emailUsuario())
                .orElseGet(() -> {
                    Usuario nuevoUsuario = new Usuario();
                    nuevoUsuario.setUsername(pedidoRequest.nombreUsuario());
                    nuevoUsuario.setEmail(pedidoRequest.emailUsuario());
                    return usuarioRepo.save(nuevoUsuario);
                });

        //lo que hace eso es inicializar crear el pedido del dto request mediante el mapper
        Pedido pedido = pedidoMapper.toEntity(pedidoRequest);

        //seteamos el usuario
        pedido.setUsuario(usuario);


        //validamos los productos
        if (pedido.getProductos() == null || pedido.getProductos().isEmpty()) {
            throw new CrearPedidoException("No se puede crear un pedido sin productos");
        }

        //seteamos fecha, estado y precio total
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("En proceso");
        Double totalCalculado = calcularTotal(pedido.getProductos());
        pedido.setTotal(totalCalculado);

        //persiste el pedido
        Pedido pedidoGuardado = repo.save(pedido);

        return pedidoMapper.toResponseDto(pedidoGuardado);
    }

    @Override
    public List<PedidoResponseDTO> listarPedidos() {
        List<Pedido> pedidos = repo.findAll();
        return pedidoMapper.pedidoResponseDTOList(pedidos);
    }

    @Override
    public PedidoResponseDTO buscarPedido(int id) {
        Pedido pedido = repo.findById(id).orElseThrow(() -> new PedidoNotFoundException("El pedido con ID: " + id + "no existe"));
        return pedidoMapper.toResponseDto(pedido);
    }

    private Double calcularTotal(List<Producto> productos) {
        return productos.stream().mapToDouble(Producto::getPrecio).sum();
    }

}
