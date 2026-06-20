package com.smartlogix.mspedidos.saga;

import com.smartlogix.mspedidos.dto.PedidoRequest;
import com.smartlogix.mspedidos.model.EstadoPedido;
import com.smartlogix.mspedidos.model.Pedido;
import com.smartlogix.mspedidos.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoSagaOrchestratorTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PedidoSagaOrchestrator saga;

    @Test
    void ejecutar_exitoso() {

        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setDestino("Santiago");

        PedidoRequest request = new PedidoRequest();
        request.setProductoId(10L);
        request.setCantidad(2);

        when(pedidoRepository.save(any(Pedido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(restTemplate.postForObject(
                anyString(),
                any(),
                eq(String.class)))
                .thenReturn("OK");

        Pedido resultado = saga.ejecutar(pedido, request);

        assertEquals(EstadoPedido.CONFIRMADO, resultado.getEstado());

        verify(restTemplate).put(anyString(), isNull());

        verify(restTemplate).postForObject(
                anyString(),
                any(),
                eq(String.class));

        verify(pedidoRepository).save(any(Pedido.class));
    }


    @Test
    void ejecutar_errorAlDescontarStock_debeCancelarPedido() {

        Pedido pedido = new Pedido();
        pedido.setId(2L);
        pedido.setDestino("Valparaíso");

        PedidoRequest request = new PedidoRequest();
        request.setProductoId(20L);
        request.setCantidad(5);

        doThrow(new RuntimeException("Error inventario"))
                .when(restTemplate)
                .put(anyString(), isNull());

        when(pedidoRepository.save(any(Pedido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Pedido resultado = saga.ejecutar(pedido, request);

        assertEquals(EstadoPedido.CANCELADO, resultado.getEstado());
        assertEquals("SAGA: Error inventario", resultado.getObservaciones());

        verify(pedidoRepository).save(any(Pedido.class));
    }




    @Test
    void ejecutar_errorAlCrearEnvio_debeCompensarStock() {

        Pedido pedido = new Pedido();
        pedido.setId(3L);
        pedido.setDestino("Concepción");

        PedidoRequest request = new PedidoRequest();
        request.setProductoId(30L);
        request.setCantidad(1);

        doNothing()
                .doThrow(new RuntimeException("Error envío"))
                .when(restTemplate)
                .put(anyString(), isNull());

        when(restTemplate.postForObject(
                anyString(),
                any(),
                eq(String.class)))
                .thenThrow(new RuntimeException("Error envío"));

        when(pedidoRepository.save(any(Pedido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Pedido resultado = saga.ejecutar(pedido, request);

        assertEquals(EstadoPedido.CANCELADO, resultado.getEstado());

        verify(restTemplate, atLeast(2))
                .put(anyString(), isNull());

        verify(pedidoRepository, atLeastOnce())
                .save(any(Pedido.class));
    }
}