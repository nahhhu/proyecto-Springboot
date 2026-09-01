package com.techlab.spring.controller;


import com.techlab.spring.dto.ProductoRequestDTO;
import com.techlab.spring.dto.ProductoResponseDTO;
import com.techlab.spring.service.ProductoService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//TODO mejorar ruteos

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "Operaciones para gestionar productos")
public class ProductController {
    private final ProductoService productoService;

    @Autowired
    public ProductController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(summary = "Listar productos", description = "Devuelve todos los productos activos")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operacion exitosa")})
    @GetMapping("/")
    public ResponseEntity<List<ProductoResponseDTO>> listarProductos() {
        List<ProductoResponseDTO> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Listar productos inactivos", description = "Devuelve productos marcados como inactivos")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operacion exitosa")})
    @GetMapping("/inactivos")
    public ResponseEntity<List<ProductoResponseDTO>> listarProductosInactivos(){
        List<ProductoResponseDTO> productos = productoService.listarProductosInactivos();
        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Obtener producto", description = "Obtener un producto por su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Encontrado"), @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerProducto(@PathVariable Integer id) {
        ProductoResponseDTO productoEncontrado = productoService.obtenerPorId(id);
        return ResponseEntity.ok(productoEncontrado);
    }

    @Operation(summary = "Buscar por nombre", description = "Buscar productos por nombre (query param 'nombre')")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operacion exitosa")})
    @GetMapping(params = "nombre")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerPorNombre(@RequestParam String nombre) {
        List<ProductoResponseDTO> productosPorNombre = productoService.obtenerPorNombre(nombre);
        return ResponseEntity.ok(productosPorNombre);
    }

    @Operation(summary = "Crear producto", description = "Crear un nuevo producto")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Creado"), @ApiResponse(responseCode = "400", description = "Datos invalidos")})
    @PostMapping("/")//recibe un solo dato(un producto)
    public ResponseEntity<ProductoResponseDTO> crearProducto(@RequestBody @Valid ProductoRequestDTO nuevo) {
        ProductoResponseDTO creado = productoService.crear(nuevo);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @Operation(summary = "Crear varios productos", description = "Crear lista de productos")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Creado"), @ApiResponse(responseCode = "400", description = "Datos invalidos")})
    @PostMapping("/list")//recibe un conjunto de datos(lista de productos)
    public ResponseEntity<List<ProductoResponseDTO>> crearProductos(@Valid @RequestBody List<ProductoRequestDTO> productos) {
        List<ProductoResponseDTO> creado = productoService.crearProductos(productos);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar producto", description = "Actualizar datos de un producto por ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Actualizado"), @ApiResponse(responseCode = "404", description = "No encontrado")})
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(@PathVariable Integer id, @Valid @RequestBody ProductoResponseDTO datos) {
        ProductoResponseDTO productoActualizado = productoService.actualizar(id, datos);
        return ResponseEntity.ok(productoActualizado);
    }

    @Operation(summary = "Desactivar producto", description = "Marcar un producto como inactivo")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Sin contenido"), @ApiResponse(responseCode = "404", description = "No encontrado")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarProducto(@PathVariable Integer id) {
        productoService.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Activar producto", description = "Marcar un producto como activo usando un patch con el campo 'activo': true")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Sin contenido"), @ApiResponse(responseCode = "400", description = "Solicitud invalida")})
    @PatchMapping("/{id}")
    public ResponseEntity<Void> activarProducto(@PathVariable Integer id, @RequestBody Map<String, Boolean> estado){
        if(Boolean.TRUE.equals(estado.get("activo"))){
            productoService.activar(id);
        }
        return ResponseEntity.noContent().build();
    }
}
