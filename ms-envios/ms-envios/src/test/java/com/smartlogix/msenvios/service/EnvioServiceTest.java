package com.smartlogix.msenvios.service;

import com.smartlogix.msenvios.dto.EnvioResponse;
import com.smartlogix.msenvios.model.Envio;
import com.smartlogix.msenvios.model.EstadoEnvio;
import com.smartlogix.msenvios.repository.EnvioRepository;
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
class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;

    @InjectMocks
    private EnvioService envioService;

    @Test
    void obtenerEnvio_existente_debeRetornarEnvio() {

        Envio envio = new Envio();
        envio.setId(1L);
        envio.setPedidoId(10L);
        envio.setDestino("Santiago");
        envio.setTransportista("Chilexpress");
        envio.setNumeroTracking("TRK-123456");
        envio.setEstado(EstadoEnvio.ASIGNADO);

        when(envioRepository.findById(1L))
                .thenReturn(Optional.of(envio));

        EnvioResponse resultado = envioService.obtenerEnvio(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(10L, resultado.getPedidoId());
        assertEquals("Santiago", resultado.getDestino());
        assertEquals("ASIGNADO", resultado.getEstado());

        verify(envioRepository).findById(1L);
    }

    @Test
    void obtenerEnvio_inexistente_debeLanzarExcepcion() {

        when(envioRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> envioService.obtenerEnvio(99L)
        );

        assertEquals("Envio no encontrado: 99", exception.getMessage());

        verify(envioRepository).findById(99L);
    }

    @Test
    void actualizarEstado_debeGuardarNuevoEstado() {

        Envio envio = new Envio();
        envio.setId(1L);
        envio.setEstado(EstadoEnvio.ASIGNADO);

        when(envioRepository.findById(1L))
                .thenReturn(Optional.of(envio));

        when(envioRepository.save(any(Envio.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        EnvioResponse resultado =
                envioService.actualizarEstado(1L, "EN_RUTA");

        assertNotNull(resultado);
        assertEquals("EN_RUTA", resultado.getEstado());

        verify(envioRepository).findById(1L);
        verify(envioRepository).save(any(Envio.class));
    }
}