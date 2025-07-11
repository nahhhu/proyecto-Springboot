package com.techlab.spring.service;

import com.techlab.spring.exception.ProductoDuplicadoException;
import com.techlab.spring.exception.ProductoNotFoundException;
import com.techlab.spring.model.Producto;
import com.techlab.spring.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService {
    private final ProductoRepository repo;

    @Autowired
    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Producto> listarProductos() {
        return repo.findAll();
    }

    @Override
    public Producto obtenerPorId(int id) {
       return repo.findById(id).orElseThrow(() -> new ProductoNotFoundException("El producto con id: " + id + " no existe"));
    }

    @Override
    public List<Producto> obtenerPorNombre(String nombre) {
        List<Producto> productos = repo.findByNombreIgnoreCase(nombre);

        if (productos.isEmpty()){
            throw new ProductoNotFoundException("No se han encontrado productos con el nombre:" + nombre );
        }
        return productos;
    }

    @Override
    public List<Producto> obtenerPorCategoria(String categoria){
       List<Producto> productos = repo.findByCategoria(categoria);
       if (productos.isEmpty()){
           throw new ProductoNotFoundException("La categoria: " + categoria + " no existe");
       }
       return productos;
    }

    @Override
    public Producto crear(Producto p) {
       boolean exists = repo.findByNombreIgnoreCase(p.getNombre()).stream().anyMatch(prod -> prod.getNombre().equalsIgnoreCase(p.getNombre()));
       if (exists){
           throw new ProductoDuplicadoException("El producto"+ p.getNombre() + " ya existe");
       }
        return repo.save(p);
    }

    @Override
    public List<Producto> crearProductos(List<Producto> productos){
        for (Producto p : productos){
            boolean exists = repo.findByNombreIgnoreCase(p.getNombre()).stream().anyMatch(prod  -> prod.getNombre().equalsIgnoreCase(p.getNombre()));
            if (exists){
                throw new ProductoDuplicadoException("El producto: " + p.getNombre() + " ya existe");
            }
        }
        return repo.saveAll(productos);
    }

    @Override
    public Producto actualizar(int id, Producto datos) {
        Producto p = obtenerPorId(id);
        if (p != null) {
            p.setNombre(datos.getNombre());
            p.setPrecio(datos.getPrecio());
            p.setCantidadStock(datos.getCantidadStock());
            p.setCategoria(datos.getCategoria());
            return repo.save(p);
        }
        return null;
    }

    @Override
    public Boolean eliminar(int id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}

