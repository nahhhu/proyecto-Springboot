package com.techlab.spring.service;

import com.techlab.spring.dto.PedidoRequestDTO;
import com.techlab.spring.entity.Pedido;
import com.techlab.spring.entity.Producto;
import com.techlab.spring.entity.Usuario;
import com.techlab.spring.exception.StockInsuficienteException;
import com.techlab.spring.mapper.PedidoMapper;
import com.techlab.spring.repository.PedidoRepository;
import com.techlab.spring.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository repo;

    @Mock
    private UsuarioRepository usuarioRepo;

    @Mock
    private PedidoMapper pedidoMapper;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    @DisplayName("Happy path: Crear pedido cuando todos los campos del DTO son correctos")
    void crearPedido_CamposValidos() {
        PedidoRequestDTO requestDTO = new PedidoRequestDTO(
                "Ezequiel",
                "ezequiel@gmail.com",
                List.of(1, 2, 3));

        Usuario usuarioFalso = new Usuario();
        usuarioFalso.setId(1);
        usuarioFalso.setUsername("Ezequiel");
        usuarioFalso.setEmail("ezequiel@gmail.com");

        Pedido pedidoFalso = new Pedido();

        Producto p1 = new Producto();
        p1.setPrecio(100.00);
        p1.setCantidadStock(2);

        Producto p2 = new Producto();
        p2.setPrecio(110.00);
        p2.setCantidadStock(2);

        Producto p3 = new Producto();
        p3.setPrecio(100.00);
        p3.setCantidadStock(2);

        pedidoFalso.setProductos(List.of(p1, p2, p3));

        when(usuarioRepo.findByEmail("ezequiel@gmail.com")).thenReturn(Optional.of(usuarioFalso));
        when(pedidoMapper.toEntity(requestDTO)).thenReturn(pedidoFalso);

        pedidoService.crearPedido(requestDTO);

        verify(usuarioRepo).findByEmail("ezequiel@gmail.com");
        verify(pedidoMapper).toEntity(requestDTO);

        assertEquals(usuarioFalso, pedidoFalso.getUsuario());
    }

    @Test
    @DisplayName("Sad path: No crea el pedido cuando el stock del producto es 0, deberia saltar la excepcion ")
    void crearPedido_FallaCuandoNoHayStock() {
        PedidoRequestDTO requestDTO = new PedidoRequestDTO(
                "Ezequiel",
                "ezequiel@gmail.com",
                List.of(1, 2)
        );
        Usuario usuarioFalso = new Usuario();
        usuarioFalso.setId(1);
        usuarioFalso.setUsername("Ezequiel");
        usuarioFalso.setEmail("ezequiel@gmail.com");

        Pedido pedidoFalso = new Pedido();
        Producto p1 = new Producto();
        p1.setPrecio(100.00);
        p1.setCantidadStock(0);


        Producto p2 = new Producto();
        p2.setPrecio(100.00);

        pedidoFalso.setProductos(List.of(p1, p2));

        when(usuarioRepo.findByEmail("ezequiel@gmail.com")).thenReturn(Optional.of(usuarioFalso));
        when(pedidoMapper.toEntity(requestDTO)).thenReturn(pedidoFalso);

        assertThrows(StockInsuficienteException.class, () -> {
            pedidoService.crearPedido(requestDTO);
        });

        verify(usuarioRepo).findByEmail("ezequiel@gmail.com");
        verify(pedidoMapper).toEntity(requestDTO);

    }

    @Test
    void listarPedidos() {
    }

    @Test
    void buscarPedido() {
    }
}