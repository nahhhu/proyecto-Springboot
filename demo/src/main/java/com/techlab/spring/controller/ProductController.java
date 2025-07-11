package com.techlab.spring.controller;


import com.techlab.spring.model.Producto;
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

    @GetMapping("/list")
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable int id) {
        Producto productoEncontrado = productoService.obtenerPorId(id);
        return ResponseEntity.ok(productoEncontrado);
    }

    @GetMapping("/find/name/{nombre}")
    public ResponseEntity<List<Producto>> obtenerPorNombre(@PathVariable String nombre) {
        List<Producto> productosPorNombre = productoService.obtenerPorNombre(nombre);
        return ResponseEntity.ok(productosPorNombre);
    }

    @GetMapping("/find/category/{categoria}")
    public ResponseEntity<List<Producto>> obtenerPorCategoria(@PathVariable  String categoria){
        List<Producto> productosPorCategoria = productoService.obtenerPorCategoria(categoria);
        return ResponseEntity.ok(productosPorCategoria);
    }

    @PostMapping("/")//recibe un solo dato(un producto)
    public ResponseEntity<Producto> crearProducto(@RequestBody @Valid Producto nuevo) {
        Producto creado = productoService.crear(nuevo);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PostMapping("/batch")//recibe un conjunto de datos(lista de productos)
    public ResponseEntity<List<Producto>> crearProductos(@Valid @RequestBody List<Producto> productos){
        List<Producto> creado = productoService.crearProductos(productos);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable int id, @Valid @RequestBody Producto datos) {
        Producto productoActualizado = productoService.actualizar(id, datos);
        return  ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable int id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
