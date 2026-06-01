package com.techlab.spring.service;

import com.techlab.spring.dto.PedidoRequestDTO;
import com.techlab.spring.entity.Pedido;
import com.techlab.spring.entity.Producto;
import com.techlab.spring.entity.Usuario;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    @DisplayName("Happy path: Crear pedido cuando todos los campos del DTO son correctos, deberia ")
    void crearPedido_CamposValidos() {
        PedidoRequestDTO requestDTO = new PedidoRequestDTO(
                "Ezequiel",
                "ezequiel@gmail.com",
                List.of(1,2,3));

        Usuario usuarioFalso = new Usuario();
        usuarioFalso.setId(1);
        usuarioFalso.setUsername("Ezequiel");
        usuarioFalso.setEmail("ezequiel@gmail.com");

        Pedido pedidoFalso = new Pedido();

        Producto p1 = new Producto();
        p1.setPrecio(100.00);

        Producto p2 = new Producto();
        p2.setPrecio(110.00);

        Producto p3 = new Producto();
        p3.setPrecio(100.00);

        pedidoFalso.setProductos(List.of(p1,p2,p3));

        when(usuarioRepo.findByEmail("ezequiel@gmail.com")).thenReturn(Optional.of(usuarioFalso));
        when(pedidoMapper.toEntity(requestDTO)).thenReturn(pedidoFalso);

        pedidoService.crearPedido(requestDTO);

        verify(usuarioRepo).findByEmail("ezequiel@gmail.com");
        verify(pedidoMapper).toEntity(requestDTO);

        assertEquals(usuarioFalso, pedidoFalso.getUsuario());
    }

    @Test
    void listarPedidos() {
    }

    @Test
    void buscarPedido() {
    }
}