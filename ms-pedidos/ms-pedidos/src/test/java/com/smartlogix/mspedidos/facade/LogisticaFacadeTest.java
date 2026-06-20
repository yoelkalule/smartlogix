package com.smartlogix.mspedidos.facade;

import com.smartlogix.mspedidos.client.NotificacionesClient;
import com.smartlogix.mspedidos.dto.PedidoRequest;
import com.smartlogix.mspedidos.factory.PedidoBase;
import com.smartlogix.mspedidos.factory.PedidoFactory;
import com.smartlogix.mspedidos.model.EstadoPedido;
import com.smartlogix.mspedidos.model.Pedido;
import com.smartlogix.mspedidos.model.TipoPedido;
import com.smartlogix.mspedidos.observer.EventManager;
import com.smartlogix.mspedidos.repository.PedidoRepository;
import com.smartlogix.mspedidos.saga.PedidoSagaOrchestrator;
import com.smartlogix.mspedidos.strategy.DescuentoStrategy;
import com.smartlogix.mspedidos.strategy.DescuentoStrategyFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogisticaFacadeTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoFactory pedidoFactory;

    @Mock
    private DescuentoStrategyFactory strategyFactory;

    @Mock
    private EventManager eventManager;

    @Mock
    private NotificacionesClient notificacionesClient;

    @Mock
    private PedidoSagaOrchestrator sagaOrchestrator;

    @InjectMocks
    private LogisticaFacade facade;

    @Test
    void procesarPedido_exitoso() {

        PedidoRequest request = new PedidoRequest();
        request.setUsuarioId(1L);
        request.setProductoId(2L);
        request.setCantidad(3);
        request.setMontoTotal(100000.0);
        request.setTipo("NACIONAL");
        request.setDestino("Santiago");

        DescuentoStrategy strategy = mock(DescuentoStrategy.class);

        when(strategy.calcular(100000.0))
                .thenReturn(90000.0);

        when(strategyFactory.getStrategy("CLIENTE"))
                .thenReturn(strategy);

        PedidoBase pedidoBase = mock(PedidoBase.class);

        when(pedidoFactory.crear(anyString(), any(Pedido.class)))
                .thenReturn(pedidoBase);

        Pedido guardado = new Pedido();
        guardado.setId(1L);
        guardado.setUsuarioId(1L);
        guardado.setProductoId(2L);
        guardado.setCantidad(3);
        guardado.setMontoTotal(90000.0);
        guardado.setTipo(TipoPedido.NACIONAL);
        guardado.setEstado(EstadoPedido.PENDIENTE);
        guardado.setDestino("Santiago");

        when(pedidoRepository.save(any(Pedido.class)))
                .thenReturn(guardado);

        Pedido confirmado = new Pedido();
        confirmado.setId(1L);
        confirmado.setEstado(EstadoPedido.CONFIRMADO);

        when(sagaOrchestrator.ejecutar(any(), any()))
                .thenReturn(confirmado);

        when(notificacionesClient.notificarPedido(1L))
                .thenReturn("OK");

        Pedido resultado =
                facade.procesarPedido(request, "CLIENTE");

        assertNotNull(resultado);

        assertEquals(EstadoPedido.CONFIRMADO,
                resultado.getEstado());

        verify(strategyFactory).getStrategy("CLIENTE");

        verify(strategy).calcular(100000.0);

        verify(pedidoFactory).crear(anyString(), any());

        verify(pedidoBase).aplicarReglas();

        verify(pedidoRepository).save(any());

        verify(eventManager)
                .notify("PEDIDO_CREADO", 1L, "CREADO");

        verify(sagaOrchestrator)
                .ejecutar(any(), eq(request));

        verify(notificacionesClient)
                .notificarPedido(1L);
    }
}