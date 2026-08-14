package com.techlab.spring.service;

import com.techlab.spring.TestObject;
import com.techlab.spring.dto.ProductoRequestDTO;
import com.techlab.spring.dto.ProductoResponseDTO;
import com.techlab.spring.entity.Categoria;
import com.techlab.spring.entity.Producto;
import com.techlab.spring.exception.CategoriaNotFoundException;
import com.techlab.spring.exception.ProductoDuplicadoException;
import com.techlab.spring.exception.ProductoNotFoundException;
import com.techlab.spring.mapper.ProductoMapper;
import com.techlab.spring.repository.CategoriaRepository;
import com.techlab.spring.repository.ProductoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    ProductoRepository repo;

    @Mock
    ProductoMapper mapper;

    @Mock
    CategoriaRepository categoriaRepository;

    @InjectMocks
    ProductoService service;

    @Test
    @DisplayName("Happy Path: Crea producto si todos los datos son validos")
    void crearProducto_CamposValidos(){
        int categoriaId = 1;
        Categoria categoria = TestObject.crearCategoriaValida();
        Producto producto = TestObject.crearProductoValido();
        ProductoRequestDTO productoRequestDTO = TestObject.productoRequestDTO();

        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        when(mapper.toEntity(productoRequestDTO)).thenReturn(producto);

        when(repo.save(any(Producto.class))).thenReturn(producto);

        service.crear(productoRequestDTO);

        verify(repo).save(producto);

        assertEquals(categoria,producto.getCategoria());
    }

    @Test
    @DisplayName("Sad Path: No crea el producto debido a que la categoria no existe, debe saltar error")
    void crearProducto_FallaCuandoCategoriaInvalida(){
        ProductoRequestDTO productoRequestDTO = TestObject.productoRequestDTO();
        int categoriaId = productoRequestDTO.categoriaId();

        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFoundException.class, () -> service.crear(productoRequestDTO));

        verify(mapper,never()).toEntity(any());
        verify(repo,never()).save(any(Producto.class));
    }

    @Test
    @DisplayName("Sad Path: No crea el producto debido que ya existe, debe saltar error")
    void crearProducto_FallaCuandoEstaDuplicado(){
        ProductoRequestDTO productoRequestDTO = TestObject.productoRequestDTO();
        String productoNombre = productoRequestDTO.nombre();

        when(repo.existsByNombreIgnoreCase(productoNombre)).thenReturn(true);
        assertThrows(ProductoDuplicadoException.class, () -> service.crear(productoRequestDTO));

        verify(mapper,never()).toEntity(any());
        verify(repo,never()).save(any(Producto.class));
    }


    @Test
    @DisplayName("Happy Path: Devuelve el producto cuando el id es correcto")
    void obtenerPorId_IdValido(){
        int productoId = 1;
        Producto producto = TestObject.crearProductoValido();
        ProductoResponseDTO productoResponseDTO = TestObject.productoResponseDTO();

        when(repo.findById(productoId)).thenReturn(Optional.of(producto));
        when(mapper.toDto(producto)).thenReturn(productoResponseDTO);

        var resultado = service.obtenerPorId(productoId);

        assertEquals(productoResponseDTO,resultado);

        verify(repo,times(1)).findById(productoId);
        verify(mapper,times(1)).toDto(producto);
    }

    @Test
    @DisplayName("Sad Path: Devuelve error debido a que el producto no existe.")
    void obtenerPorId_FallaCuandoNoExiste(){
        int productoId = 1;

        when(repo.findById(productoId)).thenReturn(Optional.empty());
        assertThrows(ProductoNotFoundException.class, () -> service.obtenerPorId(productoId));

        verify(repo, times(1)).findById(productoId);
        verify(mapper,never()).toDto(any());
    }

    @Test
    @DisplayName("Happy Path: Devuelve el producto correspondiente al nombre")
    void obtenerPorNombre_NombreValido(){
        List<Producto> productos = List.of(TestObject.crearProductoValido());
        List<ProductoResponseDTO> productoResponseDTOS = List.of(TestObject.productoResponseDTO());
        String productoNombre = "Teclado mecanico";

        when(repo.findByNombreContainingIgnoreCase(productoNombre)).thenReturn(productos);
        when(mapper.toDtoList(productos)).thenReturn(productoResponseDTOS);

        var resultado = service.obtenerPorNombre(productoNombre);

        assertEquals(productoResponseDTOS, resultado);

        verify(repo,times(1)).findByNombreContainingIgnoreCase(productoNombre);
        verify(mapper,times(1)).toDtoList(productos);
    }

}

