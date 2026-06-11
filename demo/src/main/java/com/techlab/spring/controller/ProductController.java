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

//TODO mejorar ruteos

@RestController
@RequestMapping("/producto")
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

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerProducto(@PathVariable int id) {
        ProductoResponseDTO productoEncontrado = productoService.obtenerPorId(id);
        return ResponseEntity.ok(productoEncontrado);
    }

    @GetMapping("/name/{nombre}")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerPorNombre(@PathVariable String nombre) {
        List<ProductoResponseDTO> productosPorNombre = productoService.obtenerPorNombre(nombre);
        return ResponseEntity.ok(productosPorNombre);
    }

    @GetMapping("/category/{categoria}")
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

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(@PathVariable int id, @Valid @RequestBody ProductoResponseDTO datos) {
        ProductoResponseDTO productoActualizado = productoService.actualizar(id, datos);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> desactivarProducto(@PathVariable int id) {
        productoService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
