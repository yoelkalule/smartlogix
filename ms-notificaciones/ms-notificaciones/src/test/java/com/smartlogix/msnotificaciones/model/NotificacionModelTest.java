package com.smartlogix.msnotificaciones.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificacionModelTest {

    @Test
    void emailNotificacion_getTipo_debeSerEmail() {

        EmailNotificacion email =
                new EmailNotificacion(1L, "Pedido confirmado");

        assertDoesNotThrow(email::enviar);
        assertEquals(TipoNotificacion.EMAIL, email.getTipo());
    }

    @Test
    void smsNotificacion_getTipo_debeSerSms() {

        SmsNotificacion sms =
                new SmsNotificacion(2L, "En camino");

        assertDoesNotThrow(sms::enviar);
        assertEquals(TipoNotificacion.SMS, sms.getTipo());
    }

    @Test
    void pushNotificacion_getTipo_debeSerPush() {

        PushNotificacion push =
                new PushNotificacion(3L, "Entregado");

        assertDoesNotThrow(push::enviar);
        assertEquals(TipoNotificacion.PUSH, push.getTipo());
    }
}