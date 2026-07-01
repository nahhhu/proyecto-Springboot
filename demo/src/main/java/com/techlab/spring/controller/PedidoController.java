package com.techlab.spring.controller;

import com.techlab.spring.dto.PedidoRequestDTO;
import com.techlab.spring.dto.PedidoResponseDTO;
import com.techlab.spring.entity.EstadoPedido;
import com.techlab.spring.service.PedidoService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<PedidoResponseDTO> crearPedido(@RequestBody PedidoRequestDTO pedidoRequest) {
        PedidoResponseDTO nuevo = service.crearPedido(pedidoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @GetMapping("/list")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedido() {
        List<PedidoResponseDTO> pedidos = service.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidosPorEstado(@PathVariable String estado) {
        List<PedidoResponseDTO> pedidos = service.filtrarPedidoPorEstado(EstadoPedido.valueOf(estado));
        return ResponseEntity.ok(pedidos);
    }

    @PatchMapping("/cancelar/{id}")
    public ResponseEntity<PedidoResponseDTO> cancelarPedido(@PathVariable Integer id){
        PedidoResponseDTO pedidos = service.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Integer id) {
        PedidoResponseDTO pedidoEncontrado = service.buscarPedido(id);
        return ResponseEntity.ok(pedidoEncontrado);
    }
}
