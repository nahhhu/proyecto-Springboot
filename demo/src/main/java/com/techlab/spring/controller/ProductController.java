package com.techlab.spring.controller;


import com.techlab.spring.model.Producto;
import com.techlab.spring.service.ProductoService;
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

    @GetMapping("/listar")
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    @GetMapping("/obtener/{id}")
    public Producto obtenerProducto(@PathVariable int id) {
        System.out.println("Buscando id: " + id);
        return productoService.orbtenerPordId(id);
    }

    @GetMapping("/obtener/nombre/{nombre}")
    public List<Producto> obtenerPorNombre(@PathVariable String nombre) {
        System.out.println("Buscando productos con nombre: " + nombre);
        return productoService.obtenerPorNombre(nombre);
    }

    @PostMapping("/crear")
    public Producto crearProducto(@RequestBody Producto nuevo) {
        return productoService.crear(nuevo);
    }

    @PutMapping("/actualizar/{id}")
    public Producto actualizarProducto(@PathVariable int id, @RequestBody Producto datos) {
        return productoService.actualizar(id, datos);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarProducto(@PathVariable int id) {
        productoService.eliminar(id);
    }
}
