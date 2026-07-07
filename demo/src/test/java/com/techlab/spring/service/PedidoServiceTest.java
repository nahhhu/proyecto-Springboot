package com.techlab.spring.service;

import com.techlab.spring.TestObject;
import com.techlab.spring.dto.PedidoRequestDTO;
import com.techlab.spring.dto.PedidoResponseDTO;
import com.techlab.spring.dto.UsuarioDTO;
import com.techlab.spring.entity.EstadoPedido;
import com.techlab.spring.entity.Pedido;
import com.techlab.spring.entity.Producto;
import com.techlab.spring.entity.Usuario;
import com.techlab.spring.exception.CrearPedidoException;
import com.techlab.spring.exception.PedidoEstadoInvalidoException;
import com.techlab.spring.exception.PedidoNotFoundException;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
    @DisplayName("Happy path: Crear pedido cuando todos los campos del DTO son correctos")
    void crearPedido_CamposValidos() {
        Usuario usuarioFalso = TestObject.crearUsuarioValido();
        Pedido pedidoFalso = TestObject.crearPedidoPendienteValido();

        PedidoRequestDTO requestDTO = new PedidoRequestDTO(
                usuarioFalso.getUsername(),
                usuarioFalso.getEmail(),
                List.of(1, 2, 3));

        when(usuarioRepo.findByEmail(usuarioFalso.getEmail())).thenReturn(Optional.of(usuarioFalso));
        when(pedidoMapper.toEntity(requestDTO)).thenReturn(pedidoFalso);

        when(repo.save(any(Pedido.class))).thenReturn(pedidoFalso);

        pedidoService.crearPedido(requestDTO);

        verify(usuarioRepo).findByEmail(usuarioFalso.getEmail());
        verify(pedidoMapper).toEntity(requestDTO);
        verify(repo).save(pedidoFalso);

        assertEquals(usuarioFalso,pedidoFalso.getUsuario());
        assertEquals(EstadoPedido.PENDIENTE,pedidoFalso.getEstado());
    }

    @Test
    @DisplayName("Sad path: No crea el pedido cuando el stock del producto es 0, deberia saltar la excepcion ")
    void crearPedido_FallaCuandoNoHayStock() {
        Pedido pedidoFalso = TestObject.crearPedidoPendienteValido();
        Usuario usuarioFalso = TestObject.crearUsuarioValido();

        PedidoRequestDTO requestDTO = new PedidoRequestDTO(
                usuarioFalso.getUsername(),
                usuarioFalso.getEmail(),
                List.of(1, 2)
        );

        Producto p1 = TestObject.crearProductoValido();
        p1.setCantidadStock(0);
        Producto p2 = TestObject.crearProductoValido();
        pedidoFalso.setProductos(List.of(p1, p2));

        when(pedidoMapper.toEntity(requestDTO)).thenReturn(pedidoFalso);

        assertThrows(StockInsuficienteException.class, () -> {
            pedidoService.crearPedido(requestDTO);
        });

        verify(usuarioRepo, never()).findByEmail(anyString());
        verify(pedidoMapper).toEntity(requestDTO);
    }

    @Test
    @DisplayName("Sad path: No permite realizar un pedido con la lista vacia")
    void crearPedido_FallaCuandoListaVacia(){
        Usuario usuarioFalso = TestObject.crearUsuarioValido();

        PedidoRequestDTO pedidoInvalido = new PedidoRequestDTO(
                usuarioFalso.getUsername(),
                usuarioFalso.getUsername(),
                List.of()
        );

        assertThrows(CrearPedidoException.class, () -> {
            pedidoService.crearPedido(pedidoInvalido);
        });

        verify(usuarioRepo, never()).findByEmail(anyString());
        verify(repo,never()).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Sad path: No permite cancelar un pedido cuando su estado es PAGADO")
    void cancelarPedido_EstaPagadoLanzaExcepcion(){
        Pedido pedidoPagado = TestObject.crearPedidoPendienteValido();
        pedidoPagado.setEstado(EstadoPedido.PAGADO);

        when(repo.findById(pedidoPagado.getId())).thenReturn(Optional.of(pedidoPagado));

        assertThrows(PedidoEstadoInvalidoException.class, () -> {
            pedidoService.cancelarPedido(pedidoPagado.getId());
        });

        verify(repo, never()).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Happy Path: Devuelve una lista con todos los pedidos ")
    void listarPedidos() {
        UsuarioDTO usuarioDTO = TestObject.usuarioDTO();
        Pedido pedidoFalso1 = TestObject.crearPedidoPendienteValido();
        Pedido pedidoFalso2 = TestObject.crearPedidoPendienteValido();
        List<Pedido> listaPedidosValida = List.of(pedidoFalso2,pedidoFalso1);

        PedidoResponseDTO dto1 = new PedidoResponseDTO(1, LocalDateTime.now(), EstadoPedido.PENDIENTE, 20000.00, usuarioDTO, List.of());
        PedidoResponseDTO dto2 = new PedidoResponseDTO(1, LocalDateTime.now(), EstadoPedido.PENDIENTE, 20000.00, usuarioDTO, List.of());
        List<PedidoResponseDTO> listaDatosEsperada = List.of(dto2,dto1);

        when(repo.findAll()).thenReturn(listaPedidosValida);

        when(pedidoMapper.pedidoResponseDTOList(listaPedidosValida)).thenReturn(listaDatosEsperada);

        var resultado = pedidoService.listarPedidos();

        assertEquals(2,resultado.size(),"Debe devolver exactamente 2 pedidos");
        verify(repo, times(1)).findAll();
        verify(pedidoMapper,times(1)).pedidoResponseDTOList(listaPedidosValida);
    }

    @Test
    @DisplayName("Sad Path: Devuelve una lista vacia cuando se piden pedidos y noo hay ninguno")
    void listarPedidos_DevuelveListaVaciaCuandoNoHayPedidos(){
        List<Pedido> listaPedidoVacia = List.of();
        List<PedidoResponseDTO> listaVacia = List.of();

        when(repo.findAll()).thenReturn(listaPedidoVacia);
        when(pedidoMapper.pedidoResponseDTOList(listaPedidoVacia)).thenReturn(listaVacia);

        var resultado = pedidoService.listarPedidos();

        assertEquals(0, resultado.size(), "Deve devolver 0 pedidos");

        verify(repo, times(1)).findAll();
        verify(pedidoMapper,times(1)).pedidoResponseDTOList(listaPedidoVacia);

    }

    @Test
    @DisplayName("Happy path: Devuele el pedido correspondiente al ID")
    void buscarPedido() {
        int id = 1;
        UsuarioDTO usuarioDTO = TestObject.usuarioDTO();
        Pedido pedido = TestObject.crearPedidoPendienteValido();
        PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO(id,LocalDateTime.now(),EstadoPedido.PENDIENTE,23000.00, usuarioDTO, List.of());

        when(repo.findById(id)).thenReturn(Optional.of(pedido));

        when(pedidoMapper.toResponseDto(pedido)).thenReturn(pedidoResponseDTO);

        var resultado = pedidoService.buscarPedido(id);

        assertEquals(pedidoResponseDTO,resultado);

        verify(repo, times(1)).findById(id);
        verify(pedidoMapper,times(1)).toResponseDto(pedido);
    }

    @Test
    @DisplayName("Sad Path: Devuelve error al no existir el pedido")
    void buscarPedido_LanzaErrorCuandoPedidoNoExiste(){
        int id =1;
        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThrows(PedidoNotFoundException.class, ()-> pedidoService.buscarPedido(id));

        verify(repo,times(1)).findById(id);
        verify(pedidoMapper,never()).toResponseDto(any());
    }
}