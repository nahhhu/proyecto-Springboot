package com.techlab.spring.service;

import com.techlab.spring.exception.CrearPedidoException;
import com.techlab.spring.exception.PedidoListException;
import com.techlab.spring.exception.PedidoNotFoundException;
import com.techlab.spring.model.Pedido;
import com.techlab.spring.model.Producto;
import com.techlab.spring.model.Usuario;
import com.techlab.spring.repository.PedidoRepository;
import com.techlab.spring.repository.ProductoRepository;
import com.techlab.spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService implements IPedidoService{

    @Autowired
    private  final PedidoRepository repo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private ProductoRepository productoRepo;

    public PedidoService(PedidoRepository repo){
        this.repo = repo;
    }

    @Override
    public Pedido crearPedido(Pedido pedido){
         Usuario usuario = usuarioRepo.findById(pedido.getUsuario().getId())
                         .orElseThrow(() -> new CrearPedidoException("Usuario no encontrado"));

         List<Producto> productos = pedido.getProductos().stream().map(p -> productoRepo.findById(p.getId())
                 .orElseThrow(() -> new CrearPedidoException("Producto no encontrado"))).toList();

        pedido.setEstado("En proceso");
        return repo.save(pedido);
    }

    @Override
    public List<Pedido> listarPedidos() {
        List<Pedido> pedidos = repo.findAll();

        if (pedidos.isEmpty()){
            throw new PedidoListException("No hay pedidos para mostrar");
        }

        return pedidos;
    }

    @Override
    public Pedido buscarPedido(int id) {
        Pedido pedidos = repo.findById(id).orElseThrow(() -> new PedidoNotFoundException("El pedido con ID: " + id + "no existe"));
        return pedidos;
    }
}
