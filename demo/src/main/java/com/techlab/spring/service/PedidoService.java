package com.techlab.spring.service;

import com.techlab.spring.dto.PedidoRequestDTO;
import com.techlab.spring.dto.PedidoResponseDTO;
import com.techlab.spring.entity.EstadoPedido;
import com.techlab.spring.entity.Pedido;
import com.techlab.spring.entity.Producto;
import com.techlab.spring.entity.Usuario;
import com.techlab.spring.exception.CrearPedidoException;
import com.techlab.spring.exception.PedidoEstadoInvalidoException;
import com.techlab.spring.exception.PedidoNotFoundException;
import com.techlab.spring.exception.StockInsuficienteException;
import com.techlab.spring.mapper.PedidoMapper;
import com.techlab.spring.repository.PedidoRepository;
import com.techlab.spring.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService implements IPedidoService {

    private final PedidoRepository repo;
    public PedidoMapper pedidoMapper;
    private UsuarioRepository usuarioRepo;


    public PedidoService(PedidoRepository repo, PedidoMapper pedidoMapper, UsuarioRepository usuarioRepo) {
        this.repo = repo;
        this.pedidoMapper = pedidoMapper;
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public PedidoResponseDTO crearPedido(PedidoRequestDTO pedidoRequest) {

        //lo que hace eso es inicializar crear el pedido del dto request mediante el mapper
        Pedido pedido = pedidoMapper.toEntity(pedidoRequest);


        //validamos los productos
        if (pedidoRequest.productosId() == null || pedidoRequest.productosId().isEmpty()) {
            throw new CrearPedidoException(" No se puede crear un pedido sin productos");
        }

        boolean sinStock = pedido.getProductos().stream().anyMatch(producto -> producto.getCantidadStock() <= 0);

        if (sinStock) {
            throw new StockInsuficienteException("Uno o mas productos del pedido no cuentan con stock");
        }

        //En caso de que el usuario no exista, lo crea
        Usuario usuario = usuarioRepo.findByEmail(pedidoRequest.emailUsuario())
                .orElseGet(() -> {
                    Usuario nuevoUsuario = new Usuario();
                    nuevoUsuario.setUsername(pedidoRequest.nombreUsuario());
                    nuevoUsuario.setEmail(pedidoRequest.emailUsuario());
                    return usuarioRepo.save(nuevoUsuario);
                });

        //seteamos el usuario
        pedido.setUsuario(usuario);

        //seteamos fecha, estado y precio total
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado(EstadoPedido.PENDIENTE);
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
        public PedidoResponseDTO buscarPedido(Integer id) {
        Pedido pedido = repo.findById(id).orElseThrow(() -> new PedidoNotFoundException("El pedido con ID: " + id + "no existe"));
        return pedidoMapper.toResponseDto(pedido);
    }

    public List<PedidoResponseDTO> filtrarPedidoPorEstado(EstadoPedido estado) {
        List<Pedido> pedidos = repo.findByEstado(estado);
        if (pedidos.isEmpty()) {
            throw new PedidoNotFoundException("No se encontraron pedidos con el estado: " + estado);
        }
        return pedidoMapper.pedidoResponseDTOList(pedidos);
    }

    public PedidoResponseDTO cancelarPedido(Integer id){
        Pedido pedido = repo.findById(id).orElseThrow(() -> new PedidoEstadoInvalidoException("El pedido con ID: " + id + "no existe"));
        if(pedido.getEstado() == EstadoPedido.PAGADO){
            throw new PedidoEstadoInvalidoException("No se puede cancelar un pedido que ya ha sido pagado");
        }
        if(pedido.getEstado() == EstadoPedido.CANCELADO){
            throw new CrearPedidoException("El pedido ya se encuentra cancelado");
        }
        pedido.setEstado(EstadoPedido.CANCELADO);
        Pedido pedidoActualizado = repo.save(pedido);
        return pedidoMapper.toResponseDto(pedidoActualizado);
    }

    private Double calcularTotal(List<Producto> productos) {
        return productos.stream().mapToDouble(Producto::getPrecio).sum();
    }

}
