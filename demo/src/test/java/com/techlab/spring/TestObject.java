package com.techlab.spring;

import com.techlab.spring.dto.ImagenResponseDTO;
import com.techlab.spring.dto.ProductoRequestDTO;
import com.techlab.spring.dto.ProductoResponseDTO;
import com.techlab.spring.dto.UsuarioDTO;
import com.techlab.spring.entity.*;

import java.awt.*;
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


    public static Categoria crearCategoriaValida(){
        Categoria categoria = new Categoria();
        categoria.setId(1);
        categoria.setNombre("Teclado Mecanico");
        categoria.setActiva(true);
        return  categoria;
    }

    public static ImagenResponseDTO imagenResponseDTOValida(){
        Integer productoId =1;
        return new ImagenResponseDTO(1,"imagenprueba.com","imagen de prueba",productoId);
    }

    public static ProductoRequestDTO productoRequestDTO(){
        return new ProductoRequestDTO("teclado redragon", "Teclado blanco",12000.00, 12,1);
    }

    public static ProductoResponseDTO productoResponseDTO(){
        List<ImagenResponseDTO> imagenes = List.of(imagenResponseDTOValida(),imagenResponseDTOValida());
        return new ProductoResponseDTO(1, "Teclado Redragon", 120000.00, 1, true, 1, imagenes);
    }
}
