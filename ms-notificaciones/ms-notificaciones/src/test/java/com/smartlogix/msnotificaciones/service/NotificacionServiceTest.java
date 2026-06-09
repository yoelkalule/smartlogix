package com.smartlogix.msnotificaciones.service;

import com.smartlogix.msnotificaciones.dto.NotificacionResponse;
import com.smartlogix.msnotificaciones.factory.NotificacionFactory;
import com.smartlogix.msnotificaciones.model.Notificacion;
import com.smartlogix.msnotificaciones.model.NotificacionRegistro;
import com.smartlogix.msnotificaciones.model.TipoNotificacion;
import com.smartlogix.msnotificaciones.repository.NotificacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceTest {

    @Mock
    private NotificacionFactory notificacionFactory;

    @Mock
    private NotificacionRepository notificacionRepository;

    @Mock
    private Notificacion notificacion;

    @InjectMocks
    private NotificacionService notificacionService;

    @Test
    void procesarEvento_debeGuardarNotificacion() {

        when(notificacionFactory.crear(
                eq("EMAIL"),
                eq(1L),
                anyString()))
                .thenReturn(notificacion);

        NotificacionRegistro registro = new NotificacionRegistro();
        registro.setId(1L);
        registro.setPedidoId(1L);
        registro.setTipo(TipoNotificacion.EMAIL);
        registro.setMensaje("Se creo el pedido ID 1");
        registro.setFechaEnvio(LocalDateTime.now());
        registro.setEnviado(true);

        when(notificacionRepository.save(any(NotificacionRegistro.class)))
                .thenReturn(registro);

        NotificacionResponse response =
                notificacionService.procesarEvento(1L);

        assertNotNull(response);
        assertEquals(1L, response.getPedidoId());
        assertEquals("EMAIL", response.getTipo());
        assertTrue(response.getEnviado());

        verify(notificacion).enviar();
        verify(notificacionRepository).save(any(NotificacionRegistro.class));
    }

    @Test
    void enviarNotificacionSMS_debeCrearNotificacionSMS() {

        when(notificacionFactory.crear(
                eq("SMS"),
                eq(5L),
                eq("Pedido enviado")))
                .thenReturn(notificacion);

        NotificacionRegistro registro = new NotificacionRegistro();
        registro.setId(2L);
        registro.setPedidoId(5L);
        registro.setTipo(TipoNotificacion.SMS);
        registro.setMensaje("Pedido enviado");
        registro.setFechaEnvio(LocalDateTime.now());
        registro.setEnviado(true);

        when(notificacionRepository.save(any(NotificacionRegistro.class)))
                .thenReturn(registro);

        NotificacionResponse response =
                notificacionService.enviarNotificacion(
                        5L,
                        "SMS",
                        "Pedido enviado"
                );

        assertEquals("SMS", response.getTipo());
        assertEquals("Pedido enviado", response.getMensaje());

        verify(notificacion).enviar();
        verify(notificacionRepository).save(any(NotificacionRegistro.class));
    }
}