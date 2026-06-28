package com.smartlogix.msnotificaciones.factory;

import com.smartlogix.msnotificaciones.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificacionFactoryTest {

    private final NotificacionFactory factory = new NotificacionFactory();

    @Test
    void crear_email_debeRetornarEmailNotificacion() {

        Notificacion notificacion =
                factory.crear("EMAIL", 1L, "Pedido confirmado");

        assertNotNull(notificacion);
        assertTrue(notificacion instanceof EmailNotificacion);
        assertEquals(TipoNotificacion.EMAIL, notificacion.getTipo());
    }

    @Test
    void crear_sms_debeRetornarSmsNotificacion() {

        Notificacion notificacion =
                factory.crear("SMS", 2L, "Su pedido esta en camino");

        assertNotNull(notificacion);
        assertTrue(notificacion instanceof SmsNotificacion);
        assertEquals(TipoNotificacion.SMS, notificacion.getTipo());
    }

    @Test
    void crear_push_debeRetornarPushNotificacion() {

        Notificacion notificacion =
                factory.crear("PUSH", 3L, "Entrega programada");

        assertNotNull(notificacion);
        assertTrue(notificacion instanceof PushNotificacion);
        assertEquals(TipoNotificacion.PUSH, notificacion.getTipo());
    }

    @Test
    void crear_conMinusculas_debeCrearCorrectamente() {

        Notificacion notificacion =
                factory.crear("email", 1L, "Test");

        assertTrue(notificacion instanceof EmailNotificacion);
    }

    @Test
    void crear_tipoInvalido_debeLanzarExcepcion() {

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> factory.crear("FAX", 1L, "Test")
        );

        assertEquals("Tipo desconocido: FAX", ex.getMessage());
    }
}