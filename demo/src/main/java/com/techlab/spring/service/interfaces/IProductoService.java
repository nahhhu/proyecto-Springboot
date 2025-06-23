package com.techlab.spring.service.interfaces;

import com.techlab.spring.model.Producto;

import java.util.List;

public interface IProductoService {
    //String crearProducto(Producto producto);
    List <Producto> listarProductos();
    Producto buscarPorId(Integer id);

}
