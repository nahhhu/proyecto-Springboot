package com.techlab.spring.service;

import com.techlab.spring.dto.ProductoRequestDTO;
import com.techlab.spring.dto.ProductoResponseDTO;
import com.techlab.spring.entity.Producto;
import com.techlab.spring.exception.ProductoDuplicadoException;
import com.techlab.spring.exception.ProductoNotFoundException;
import com.techlab.spring.mapper.ProductoMapper;
import com.techlab.spring.repository.ProductoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService implements IProductoService {

    private final ProductoRepository repo;
    private final ProductoMapper mapper;

    @Override
    public List<ProductoResponseDTO> listarProductos() {
        return mapper.toDtoList(repo.findAll());
    }

    @Override
    public ProductoResponseDTO obtenerPorId(Integer id) {
        return repo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ProductoNotFoundException("El producto con el id: " + id + " no existe"));
    }

    @Override
    public List<ProductoResponseDTO> obtenerPorNombre(String nombre) {
        List<Producto> productos = repo.findByNombreContainingIgnoreCase(nombre);

        if (productos.isEmpty()) {
            throw new ProductoNotFoundException("No se han encontrado productos con el nombre:" + nombre);
        }
        return mapper.toDtoList(productos);
    }

    @Override
    public List<ProductoResponseDTO> obtenerPorCategoria(Integer categoriaId) {
        List<Producto> productos = repo.findByCategoriaId(categoriaId);
        if (productos.isEmpty()) {
            throw new ProductoNotFoundException("La categoria: " + categoriaId + " no existe");
        }
        return mapper.toDtoList(productos);
    }

    @Override
    public ProductoResponseDTO crear(@Valid ProductoRequestDTO p) {
        if (repo.existsByNombreIgnoreCase(p.nombre())) {
            throw new ProductoDuplicadoException("El producto " + p.nombre() + " ya existe");
        }

        Producto entidad = mapper.toEntity(p);
        Producto guardado = repo.save(entidad);
        return mapper.toDto(guardado);
    }

    @Override
    public List<ProductoResponseDTO> crearProductos(List<ProductoRequestDTO> productos) {
        for (ProductoRequestDTO p : productos) {
            if (repo.existsByNombreIgnoreCase(p.nombre())) {
                throw new ProductoDuplicadoException("El producto: " + p.nombre() + " ya existe");
            }
        }

        List<Producto> entidades = mapper.toEntityList(productos);
        List<Producto> guardados = repo.saveAll(entidades);
        return mapper.toDtoList(guardados);
    }

    @Override
    public ProductoResponseDTO actualizar(Integer id, ProductoResponseDTO datos) {
        Producto existe = repo.findById(id).orElseThrow(() -> new ProductoNotFoundException("No se puede actualizar. El producto con id " + id + " no existe."));

        mapper.updateEntityFromDto(datos, existe);

        Producto guardado = repo.save(existe);
        return mapper.toDto(guardado);
    }

    @Override
    public boolean desactivar(Integer id) {
        Producto producto = repo.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("El producto con id: " + id + " no existe"));
        producto.setActivo(false);
        repo.save(producto);
        return true;
    }

    @Override
    public boolean activar(Integer id) {
        Producto producto = repo.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("El producto con id: " + id + " no existe."));
        producto.setActivo(true);
        repo.save(producto);
        return true;
    }

}

