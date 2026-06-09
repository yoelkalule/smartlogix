package com.smartlogix.mspedidos.service;

import com.smartlogix.mspedidos.dto.PedidoResponse;
import com.smartlogix.mspedidos.facade.LogisticaFacade;
import com.smartlogix.mspedidos.model.EstadoPedido;
import com.smartlogix.mspedidos.model.Pedido;
import com.smartlogix.mspedidos.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private LogisticaFacade logisticaFacade;

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void obtenerPedido_existente_debeRetornarPedido() {

        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setUsuarioId(1L);
        pedido.setProductoId(1L);
        pedido.setCantidad(2);
        pedido.setMontoTotal(50000.0);
        pedido.setEstado(EstadoPedido.PENDIENTE);

        when(pedidoRepository.findById(1L))
                .thenReturn(Optional.of(pedido));

        PedidoResponse resultado = pedidoService.obtenerPedido(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(1L, resultado.getUsuarioId());
        assertEquals(1L, resultado.getProductoId());

        verify(pedidoRepository).findById(1L);
    }

    @Test
    void obtenerPedido_inexistente_debeLanzarExcepcion() {

        when(pedidoRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> pedidoService.obtenerPedido(99L)
        );

        assertEquals(
                "Pedido no encontrado: 99",
                exception.getMessage()
        );

        verify(pedidoRepository).findById(99L);
    }

    @Test
    void actualizarEstado_debeGuardarNuevoEstado() {

        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setUsuarioId(1L);
        pedido.setProductoId(1L);
        pedido.setCantidad(1);
        pedido.setMontoTotal(50000.0);
        pedido.setEstado(EstadoPedido.PENDIENTE);

        when(pedidoRepository.findById(1L))
                .thenReturn(Optional.of(pedido));

        when(pedidoRepository.save(any(Pedido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PedidoResponse resultado =
                pedidoService.actualizarEstado(1L, "ENTREGADO");

        assertEquals("ENTREGADO", resultado.getEstado());

        verify(pedidoRepository).findById(1L);
        verify(pedidoRepository).save(any(Pedido.class));
    }
}