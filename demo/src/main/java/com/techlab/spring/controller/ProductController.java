package com.techlab.spring.controller;


import com.techlab.spring.model.Producto;
import com.techlab.spring.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    @GetMapping("/find/{id}")
    public Producto obtenerProducto(@PathVariable int id) {
        System.out.println("Buscando id: " + id);
        return productoService.obtenerPorId(id);
    }

    @GetMapping("/find/name/{nombre}")
    public List<Producto> obtenerPorNombre(@PathVariable String nombre) {
        System.out.println("Buscando productos con nombre: " + nombre);
        return productoService.obtenerPorNombre(nombre);
    }

    @GetMapping("/find/category/{categoria}")
    public List<Producto> obtenerPorCategoria(@PathVariable String categoria){
        System.out.println("Buscando por la categoria" + categoria);
        return productoService.obtenerPorCategoria(categoria);
    }

    @PostMapping("/")//recibe un solo dato(un producto)
    public Producto crearProducto(@Valid @RequestBody Producto nuevo) {
        return productoService.crear(nuevo);
    }

    @PostMapping("/batch")//recibe un conjunto de datos(lista de productos)
    public List<Producto> crearProductos(@Valid @RequestBody List<Producto> productos){
        return productoService.crearProductos(productos);
    }

    @PutMapping("/update/{id}")
    public Producto actualizarProducto(@PathVariable int id, @Valid @RequestBody Producto datos) {
        return productoService.actualizar(id, datos);
    }

    @DeleteMapping("/delete/{id}")
    public void eliminarProducto(@PathVariable int id) {
        productoService.eliminar(id);
    }
}
