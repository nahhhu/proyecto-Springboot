package com.techlab.spring.controller;

import com.techlab.spring.dto.PedidoRequestDTO;
import com.techlab.spring.dto.PedidoResponseDTO;
import com.techlab.spring.entity.EstadoPedido;
import com.techlab.spring.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedidos", description = "Operaciones para gestionar pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @Operation(summary = "Crear pedido", description = "Crear un nuevo pedido con lista de productos")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Creado"), @ApiResponse(responseCode = "400", description = "Datos invalidos")})
    @PostMapping("/")
    public ResponseEntity<PedidoResponseDTO> crearPedido(@RequestBody PedidoRequestDTO pedidoRequest) {
        PedidoResponseDTO nuevo = service.crearPedido(pedidoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @Operation(summary = "Listar pedidos", description = "Devuelve todos los pedidos")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operacion exitosa")})
    @GetMapping("/list")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedido() {
        List<PedidoResponseDTO> pedidos = service.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Filtrar por estado", description = "Filtrar pedidos por estado (Pendiente, Cancelado, etc)")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operacion exitosa")})
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidosPorEstado(@PathVariable String estado) {
        List<PedidoResponseDTO> pedidos = service.filtrarPedidoPorEstado(EstadoPedido.valueOf(estado));
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Cancelar pedido", description = "Cancela un pedido especificado por ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Cancelado"), @ApiResponse(responseCode = "404", description = "No encontrado")})
    @PatchMapping("/cancelar/{id}")
    public ResponseEntity<PedidoResponseDTO> cancelarPedido(@PathVariable Integer id){
        service.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar pedido por ID", description = "Obtener informacion de un pedido por su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Encontrado"), @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Integer id) {
        PedidoResponseDTO pedidoEncontrado = service.buscarPedido(id);
        return ResponseEntity.ok(pedidoEncontrado);
    }
}
