package com.smartlogix.mspedidos.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificacionesClientTest {

    @Test
    void fallbackNotificarDebeRetornarMensaje() {

        NotificacionesClient client = new NotificacionesClient();

        String respuesta = client.fallbackNotificar(
                1L,
                new RuntimeException("Servicio caído")
        );

        assertNotNull(respuesta);

        assertEquals(
                "Notificacion pendiente - ms-notificaciones no disponible",
                respuesta
        );
    }

    @Test
    void constructorDebeCrearCliente() {

        NotificacionesClient client = new NotificacionesClient();

        assertNotNull(client);
    }
}