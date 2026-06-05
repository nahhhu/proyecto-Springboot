package com.techlab.spring.service;

import com.techlab.spring.dto.ProductoDTO;
import com.techlab.spring.entity.Producto;
import com.techlab.spring.exception.ProductoDuplicadoException;
import com.techlab.spring.exception.ProductoNotFoundException;
import com.techlab.spring.mapper.ProductoMapper;
import com.techlab.spring.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService implements IProductoService {

    private final ProductoRepository repo;
    private final ProductoMapper mapper;

    @Override
    public List<ProductoDTO> listarProductos() {
        return mapper.toDtoList(repo.findAll());
    }

    @Override
    public ProductoDTO obtenerPorId(Integer id) {
        return repo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ProductoNotFoundException("El producto con el id: " + id + " no existe"));
    }

    @Override
    public List<ProductoDTO> obtenerPorNombre(String nombre) {
        List<Producto> productos = repo.findByNombreContainingIgnoreCase(nombre);

        if (productos.isEmpty()) {
            throw new ProductoNotFoundException("No se han encontrado productos con el nombre:" + nombre);
        }
        return mapper.toDtoList(productos);
    }

    @Override
    public List<ProductoDTO> obtenerPorCategoria(Integer categoriaId) {
        List<Producto> productos = repo.findByCategoriaId(categoriaId);
        if (productos.isEmpty()) {
            throw new ProductoNotFoundException("La categoria: " + categoriaId + " no existe");
        }
        return mapper.toDtoList(productos);
    }

    @Override
    public ProductoDTO crear(ProductoDTO p) {
        if (repo.existsByNombreIgnoreCase(p.nombre())) {
            throw new ProductoDuplicadoException("El producto " + p.nombre() + " ya existe");
        }

        Producto entidad = mapper.toEntity(p);
        Producto guardado = repo.save(entidad);
        return mapper.toDto(guardado);
    }

    @Override
    public List<ProductoDTO> crearProductos(List<ProductoDTO> productos) {
        for (ProductoDTO p : productos) {
            if (repo.existsByNombreIgnoreCase(p.nombre())) {
                throw new ProductoDuplicadoException("El producto: " + p.nombre() + " ya existe");
            }
        }

        List<Producto> entidades = mapper.toEntityList(productos);
        List<Producto> guardados = repo.saveAll(entidades);
        return mapper.toDtoList(guardados);
    }

    @Override
    public ProductoDTO actualizar(Integer id, ProductoDTO datos) {
        Producto existe = repo.findById(id).orElseThrow(() -> new ProductoNotFoundException("No se puede actualizar. El producto con id " + id + " no existe."));

        mapper.updateEntityFromDto(datos, existe);

        Producto guardado = repo.save(existe);
        return mapper.toDto(guardado);
    }

    @Override
    public Boolean eliminar(Integer id) {
        if (!repo.existsById(id)) {
            throw new ProductoNotFoundException("No se puede eliminar. El producto con id:" + id + " no existe ");
        }

        repo.deleteById(id);
        return true;
    }
}

