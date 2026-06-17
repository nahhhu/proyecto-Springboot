package com.techlab.spring.controller;


import com.techlab.spring.dto.ProductoRequestDTO;
import com.techlab.spring.dto.ProductoResponseDTO;
import com.techlab.spring.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//TODO mejorar ruteos

@RestController
@RequestMapping("/api/v1/productos")
public class ProductController {
    private final ProductoService productoService;

    @Autowired
    public ProductController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductoResponseDTO>> listarProductos() {
        List<ProductoResponseDTO> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/inactivos")
    public ResponseEntity<List<ProductoResponseDTO>> listarProductosInactivos(){
        List<ProductoResponseDTO> productos = productoService.listarProductosInactivos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerProducto(@PathVariable Integer id) {
        ProductoResponseDTO productoEncontrado = productoService.obtenerPorId(id);
        return ResponseEntity.ok(productoEncontrado);
    }

    @GetMapping(params = "nombre")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerPorNombre(@RequestParam String nombre) {
        List<ProductoResponseDTO> productosPorNombre = productoService.obtenerPorNombre(nombre);
        return ResponseEntity.ok(productosPorNombre);
    }

    @GetMapping("/categorias/{categoriaId}/productos")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerPorCategoria(@PathVariable Integer categoriaId) {
        List<ProductoResponseDTO> productosPorCategoria = productoService.obtenerPorCategoria(categoriaId);
        return ResponseEntity.ok(productosPorCategoria);
    }

    @PostMapping("/")//recibe un solo dato(un producto)
    public ResponseEntity<ProductoResponseDTO> crearProducto(@RequestBody @Valid ProductoRequestDTO nuevo) {
        ProductoResponseDTO creado = productoService.crear(nuevo);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PostMapping("/list")//recibe un conjunto de datos(lista de productos)
    public ResponseEntity<List<ProductoResponseDTO>> crearProductos(@Valid @RequestBody List<ProductoRequestDTO> productos) {
        List<ProductoResponseDTO> creado = productoService.crearProductos(productos);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(@PathVariable Integer id, @Valid @RequestBody ProductoResponseDTO datos) {
        ProductoResponseDTO productoActualizado = productoService.actualizar(id, datos);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarProducto(@PathVariable Integer id) {
        productoService.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> activarProducto(@PathVariable Integer id, @RequestBody Map<String, Boolean> estado){
        if(Boolean.TRUE.equals(estado.get("activo"))){
            productoService.activar(id);
        }
        return ResponseEntity.noContent().build();
    }
}
