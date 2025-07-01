package com.techlab.spring.service;

import com.techlab.spring.model.Producto;
import com.techlab.spring.service.interfaces.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository repo;

    @Autowired
    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    public List<Producto> listarProductos() {
        return repo.findAll();
    }

    public Producto orbtenerPordId(int id) {
        return repo.findById(id).orElse(null);
    }

    public List<Producto> obtenerPorNombre(String nombre) {
        return repo.findByNombreIgnoreCase(nombre);
    }

    public Producto crear(Producto p) {
        return repo.save(p);
    }

    public Producto actualizar(int id, Producto datos) {
        Producto p = orbtenerPordId(id);
        if (p != null) {
            p.setNombre(datos.getNombre());
            p.setPrecio(datos.getPrecio());
            p.setCantidadStock(datos.getCantidadStock());
            p.setCategoria(datos.getCategoria());
            p.setDescripcion(datos.getDescripcion());
            return repo.save(p);
        }
        return null;
    }

    public boolean eliminar(int id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}

