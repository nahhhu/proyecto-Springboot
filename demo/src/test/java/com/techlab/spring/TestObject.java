package com.techlab.spring;

import com.techlab.spring.dto.UsuarioDTO;
import com.techlab.spring.entity.*;

import java.util.ArrayList;
import java.util.List;

public class TestObject {

    public static Producto crearProductoValido(){
        Categoria categoriaTest = new Categoria();
        categoriaTest.setNombre("Mouse");
        categoriaTest.setId(1);

        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("Mouse Redragon");
        producto.setPrecio(100000.0);
        producto.setCantidadStock(5);
        producto.setCategoria(categoriaTest);
        producto.setActivo(true);
       return producto;
    }

    public static Usuario crearUsuarioValido(){
        Usuario u = new Usuario();
        u.setId(1);
        u.setUsername("Carlos");
        u.setEmail("carlito@gmail.com");
        return u;
    }

    public static Pedido crearPedidoPendienteValido(){
        Pedido pedido = new Pedido();
        pedido.setId(400);
        pedido.setUsuario(crearUsuarioValido());

        List<Producto> productosList = new ArrayList<>();
        productosList.add(crearProductoValido());
        pedido.setProductos(productosList);

        pedido.setEstado(EstadoPedido.PENDIENTE);

        pedido.setTotal(100000);

        return pedido;
    }

    public static UsuarioDTO usuarioDTO (){
        return  new UsuarioDTO(1, "Carlo", "carlito@gmail.com");
    }

}
