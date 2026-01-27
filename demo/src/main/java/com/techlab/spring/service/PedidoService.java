package com.techlab.spring.service;

import com.techlab.spring.dto.PedidoRequestDTO;
import com.techlab.spring.dto.PedidoResponseDTO;
import com.techlab.spring.entity.Pedido;
import com.techlab.spring.entity.Producto;
import com.techlab.spring.exception.CrearPedidoException;
import com.techlab.spring.exception.PedidoListException;
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
        //lo que hace eso es inicializar crear el pedido del dto request mediante el mapper
        Pedido pedido = pedidoMapper.toEntity(pedidoRequest);

        if (pedido.getProductos() == null || pedido.getProductos().isEmpty()) {
            throw new CrearPedidoException("No se puede crear un pedido sin productos");
        }

        //seteamos fecha, estado y precio total
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("En proceso");

        Double totalCalculado = calcularTotal(pedido.getProductos());
        pedido.setTotal(totalCalculado);

        //persiste el objeto enla base de datos y repcupera el objeto gestionadoo
        Pedido pedidoGuardado = repo.save(pedido);

        //lo devuelve el pedido guardado al responsedto mediante el mapper
        return pedidoMapper.toResponseDto(pedidoGuardado);

    }

    @Override
    public List<Pedido> listarPedidos() {
        List<Pedido> pedidos = repo.findAll();

        if (pedidos.isEmpty()) {
            throw new PedidoListException("No hay pedidos para mostrar");
        }

        return pedidos;
    }

    @Override
    public Pedido buscarPedido(int id) {
        Pedido pedidos = repo.findById(id).orElseThrow(() -> new PedidoNotFoundException("El pedido con ID: " + id + "no existe"));
        return pedidos;
    }

    private Double calcularTotal(List<Producto> productos) {
        return productos.stream().mapToDouble(Producto::getPrecio).sum();
    }

}
