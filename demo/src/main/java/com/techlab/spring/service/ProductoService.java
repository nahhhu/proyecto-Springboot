package com.techlab.spring.service;

import com.techlab.spring.model.Producto;
import com.techlab.spring.service.interfaces.IProductoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProductoService implements IProductoService {
    //simular un base de datos en memoria

    private final List<Producto> productos = new ArrayList<>();
    private int contadorId = 1;

    public Producto guardar(Producto p){
        p.setId(contadorId++);
        productos.add(p);
        return  p;
    }

    public Producto actualizar(int id,Producto producto){
        Producto p = buscarPorId(id);
        if  (p != null){
            p.setNombre(p.getNombre());
            p.setPrecio(p.getPrecio());
        }
        return p;
    }

    public boolean eliminar(int id){
        return productos.removeIf(p -> p.getId() == id );
    }

    @Override
    public List<Producto> listarProductos() {
        return productos;
    }
    @Override
    public Producto buscarPorId(Integer id) {
        if(id  >= 0 && id < productos.size()){
            return productos.get(id);
        }
        return null;

    /*@Override
    public String crearProducto(Producto producto) {
        producto.setPrecio((producto.getPrecio()));
        productos.add(producto);
        return "Producto creado correctamente";
    }
    }*/
}
}
