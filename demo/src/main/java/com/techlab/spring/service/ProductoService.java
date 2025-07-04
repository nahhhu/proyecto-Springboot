package com.techlab.spring.service;

import com.techlab.spring.exception.ProductExistsException;
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
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Producto> obtenerPorNombre(String nombre) {
        return repo.findByNombreIgnoreCase(nombre);
    }

    @Override
    public List<Producto> obtenerPorCategoria(String categoria){
        return repo.findByCategoria(categoria);
    }

    @Override
    public Producto crear(Producto p) {
        boolean exists = repo.findByNombreIgnoreCase(p.getNombre()).stream().anyMatch(prod ->prod.getNombre().equalsIgnoreCase(p.getNombre()));
        if (exists){
            throw new ProductExistsException("El producto '" + p.getNombre() +"' ya existe");
        }
        return repo.save(p);
    }

    @Override
    public List<Producto> crearProductos(List<Producto> productos){
        for (Producto p : productos) {
            boolean exists = repo.findByNombreIgnoreCase(p.getNombre()).stream().anyMatch(prod -> prod.getNombre().equalsIgnoreCase(p.getNombre()));
            if (exists) {
                throw new ProductExistsException("Alguno de los productos ingresados ya existen");
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

