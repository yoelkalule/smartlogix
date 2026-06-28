package com.smartlogix.msnotificaciones.controller;

import com.smartlogix.msnotificaciones.dto.NotificacionResponse;
import com.smartlogix.msnotificaciones.service.NotificacionService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificacionControllerTest {

    @Test
    void test_debeRetornarMensaje() {

        NotificacionService service = mock(NotificacionService.class);
        NotificacionController controller =
                new NotificacionController(service);

        assertEquals("ms-notificaciones funcionando", controller.test());
    }

    @Test
    void recibirNotificacion_debeRetornarResponse() {

        NotificacionService service = mock(NotificacionService.class);

        NotificacionResponse response = new NotificacionResponse(
                1L, 1L, "EMAIL",
                "Se creo el pedido ID 1",
                LocalDateTime.now(), true
        );

        when(service.procesarEvento(1L)).thenReturn(response);

        NotificacionController controller =
                new NotificacionController(service);

        assertEquals(response,
                controller.recibirNotificacion(1L).getBody());

        verify(service).procesarEvento(1L);
    }

    @Test
    void enviarConFactory_debeRetornarResponse() {

        NotificacionService service = mock(NotificacionService.class);

        NotificacionResponse response = new NotificacionResponse(
                2L, 5L, "SMS", "Pedido en camino",
                LocalDateTime.now(), true
        );

        when(service.enviarNotificacion(5L, "SMS", "Pedido en camino"))
                .thenReturn(response);

        NotificacionController controller =
                new NotificacionController(service);

        assertEquals(response,
                controller.enviarConFactory(5L, "SMS", "Pedido en camino").getBody());

        verify(service).enviarNotificacion(5L, "SMS", "Pedido en camino");
    }

    @Test
    void listarTodas_debeRetornarLista() {

        NotificacionService service = mock(NotificacionService.class);

        when(service.listarTodas()).thenReturn(List.of(
                new NotificacionResponse(1L, 1L, "EMAIL",
                        "Test", LocalDateTime.now(), true)
        ));

        NotificacionController controller =
                new NotificacionController(service);

        assertEquals(1, controller.listarTodas().getBody().size());
        verify(service).listarTodas();
    }

    @Test
    void listarPorPedido_debeRetornarLista() {

        NotificacionService service = mock(NotificacionService.class);

        when(service.listarPorPedido(10L)).thenReturn(List.of(
                new NotificacionResponse(1L, 10L, "PUSH",
                        "Notificacion", LocalDateTime.now(), true)
        ));

        NotificacionController controller =
                new NotificacionController(service);

        List<NotificacionResponse> lista =
                controller.listarPorPedido(10L).getBody();

        assertEquals(1, lista.size());
        assertEquals(10L, lista.get(0).getPedidoId());
        verify(service).listarPorPedido(10L);
    }
}