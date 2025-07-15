package com.techlab.spring.controller;

import com.techlab.spring.model.Pedido;
import com.techlab.spring.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service){
        this.service = service;
    }

    @PostMapping("/crear")
    public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido pedido){
        Pedido nuevo = service.crearPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Pedido>> listarPedido(){
        List<Pedido> pedidos = service.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable int id){
        Pedido pedidoEncontrado = service.buscarPedido(id);
        return ResponseEntity.ok(pedidoEncontrado);
    }
}
