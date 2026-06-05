package com.techlab.spring.controller;


import com.techlab.spring.dto.ProductoDTO;
import com.techlab.spring.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductController {
    private final ProductoService productoService;

    @Autowired
    public ProductController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductoDTO>> listarProductos() {
        List<ProductoDTO> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerProducto(@PathVariable int id) {
        ProductoDTO productoEncontrado = productoService.obtenerPorId(id);
        return ResponseEntity.ok(productoEncontrado);
    }

    @GetMapping("/name/{nombre}")
    public ResponseEntity<List<ProductoDTO>> obtenerPorNombre(@PathVariable String nombre) {
        List<ProductoDTO> productosPorNombre = productoService.obtenerPorNombre(nombre);
        return ResponseEntity.ok(productosPorNombre);
    }

    @GetMapping("/category/{categoria}")
    public ResponseEntity<List<ProductoDTO>> obtenerPorCategoria(@PathVariable Integer categoriaId) {
        List<ProductoDTO> productosPorCategoria = productoService.obtenerPorCategoria(categoriaId);
        return ResponseEntity.ok(productosPorCategoria);
    }

    @PostMapping("/")//recibe un solo dato(un producto)
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody @Valid ProductoDTO nuevo) {
        ProductoDTO creado = productoService.crear(nuevo);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PostMapping("/list")//recibe un conjunto de datos(lista de productos)
    public ResponseEntity<List<ProductoDTO>> crearProductos(@Valid @RequestBody List<ProductoDTO> productos) {
        List<ProductoDTO> creado = productoService.crearProductos(productos);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable int id, @Valid @RequestBody ProductoDTO datos) {
        ProductoDTO productoActualizado = productoService.actualizar(id, datos);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable int id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
