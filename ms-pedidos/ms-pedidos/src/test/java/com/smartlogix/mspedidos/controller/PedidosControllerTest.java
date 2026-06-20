package com.smartlogix.mspedidos.controller;

import com.smartlogix.mspedidos.dto.PedidoRequest;
import com.smartlogix.mspedidos.dto.PedidoResponse;
import com.smartlogix.mspedidos.service.PedidoService;
import com.smartlogix.mspedidos.strategy.DescuentoStrategy;
import com.smartlogix.mspedidos.strategy.DescuentoStrategyFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PedidosControllerTest {

    @Test
    void test() {

        PedidoService service = mock(PedidoService.class);
        DescuentoStrategyFactory factory = mock(DescuentoStrategyFactory.class);

        PedidosController controller =
                new PedidosController(service, factory);

        assertEquals(
                "ms-pedidos funcionando",
                controller.test()
        );
    }

    @Test
    void listar() {

        PedidoService service = mock(PedidoService.class);
        DescuentoStrategyFactory factory = mock(DescuentoStrategyFactory.class);

        when(service.listarPedidos()).thenReturn(List.of());

        PedidosController controller =
                new PedidosController(service, factory);

        assertEquals(
                0,
                controller.listar().getBody().size()
        );

        verify(service).listarPedidos();
    }

    @Test
    void obtener() {

        PedidoService service = mock(PedidoService.class);
        DescuentoStrategyFactory factory = mock(DescuentoStrategyFactory.class);

        PedidoResponse response =
                new PedidoResponse(
                        1L,
                        1L,
                        2L,
                        3,
                        1000.0,
                        "PENDIENTE",
                        "Santiago",
                        null
                );

        when(service.obtenerPedido(1L)).thenReturn(response);

        PedidosController controller =
                new PedidosController(service, factory);

        assertEquals(
                response,
                controller.obtener(1L).getBody()
        );

        verify(service).obtenerPedido(1L);
    }

    @Test
    void actualizarEstado() {

        PedidoService service = mock(PedidoService.class);
        DescuentoStrategyFactory factory = mock(DescuentoStrategyFactory.class);

        PedidoResponse response =
                new PedidoResponse(
                        1L,
                        1L,
                        2L,
                        3,
                        1000.0,
                        "ENTREGADO",
                        "Santiago",
                        null
                );

        when(service.actualizarEstado(1L, "ENTREGADO"))
                .thenReturn(response);

        PedidosController controller =
                new PedidosController(service, factory);

        assertEquals(
                "ENTREGADO",
                controller.actualizarEstado(1L, "ENTREGADO")
                        .getBody()
                        .getEstado()
        );
    }

    @Test
    void listarPorUsuario() {

        PedidoService service = mock(PedidoService.class);
        DescuentoStrategyFactory factory = mock(DescuentoStrategyFactory.class);

        when(service.listarPorUsuario(1L))
                .thenReturn(List.of());

        PedidosController controller =
                new PedidosController(service, factory);

        assertEquals(
                0,
                controller.listarPorUsuario(1L).getBody().size()
        );
    }

    @Test
    void crearPedido() {

        PedidoService service = mock(PedidoService.class);
        DescuentoStrategyFactory factory = mock(DescuentoStrategyFactory.class);

        PedidoRequest request = new PedidoRequest();

        PedidoResponse response =
                new PedidoResponse(
                        1L,
                        1L,
                        1L,
                        2,
                        1000.0,
                        "PENDIENTE",
                        "Santiago",
                        null
                );

        when(service.crearPedido(any(), eq("NORMAL")))
                .thenReturn(response);

        PedidosController controller =
                new PedidosController(service, factory);

        assertEquals(
                response,
                controller.crear(request, "NORMAL").getBody()
        );
    }

    @Test
    void calcularDescuento() {

        PedidoService service = mock(PedidoService.class);
        DescuentoStrategyFactory factory = mock(DescuentoStrategyFactory.class);
        DescuentoStrategy strategy = mock(DescuentoStrategy.class);

        when(factory.getStrategy("VIP")).thenReturn(strategy);
        when(strategy.calcular(1000.0)).thenReturn(900.0);
        when(strategy.getNombre()).thenReturn("VIP");

        PedidosController controller =
                new PedidosController(service, factory);

        String body =
                controller.calcularDescuento("1000", "VIP")
                        .getBody();

        assertEquals(true, body.contains("VIP"));
    }
}