package com.techlab.spring.controller;


import com.techlab.spring.model.Producto;
import com.techlab.spring.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductController{
    private final ProductoService productoService;
    @Autowired
    public ProductController(ProductoService productoService){
        this.productoService = productoService;
    }

    @GetMapping("/list")
    public List<Producto> listaProductos(){
        return productoService.listarProductos();
    }

    @PostMapping("/")
    public Producto crearProducto(@RequestBody Producto producto){
        productoService.guardar(producto);
        return producto;
    }


    //pathvariable aclara que el valor que toma buscarproducto es el valor de getmapping y devuelve eso
    @GetMapping("/find/{id}")
    public String buscarProductos(@PathVariable Long id){
        return "Buscando..." +  id;
        //deberia ir la logica para buscar un producto
    }

    // ../find/342 -> devuelve producto cuyo id es 342
    //  ../find/{id}/precio -> El endpoint es dinamico, solo es dinamico el id.

    @GetMapping("/buscar")
    public String buscarProducto(@RequestParam String nombre, @RequestParam(required = false,  defaultValue = "asc") String orden ){
        return "Buscando... nombre  " + nombre + "Orden: " + orden;
    }

}
