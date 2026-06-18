package com.smartlogix.msenvios.service;

import com.smartlogix.msenvios.dto.EnvioRequest;
import com.smartlogix.msenvios.dto.EnvioResponse;
import com.smartlogix.msenvios.model.Envio;
import com.smartlogix.msenvios.model.EstadoEnvio;
import com.smartlogix.msenvios.repository.EnvioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
    void crearEnvio_debeGuardarCorrectamente() {

        EnvioRequest request = new EnvioRequest();
        request.setPedidoId(50L);
        request.setDestino("Valparaiso");
        request.setTransportista("Starken");

        Envio guardado = new Envio();
        guardado.setId(1L);
        guardado.setPedidoId(50L);
        guardado.setDestino("Valparaiso");
        guardado.setTransportista("Starken");
        guardado.setNumeroTracking("TRK-12345678");
        guardado.setEstado(EstadoEnvio.ASIGNADO);

        when(envioRepository.save(any(Envio.class)))
                .thenReturn(guardado);

        EnvioResponse response = envioService.crearEnvio(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(50L, response.getPedidoId());
        assertEquals("Valparaiso", response.getDestino());
        assertEquals("Starken", response.getTransportista());
        assertEquals("ASIGNADO", response.getEstado());
        assertNotNull(response.getNumeroTracking());

        verify(envioRepository).save(any(Envio.class));
    }

    @Test
    void listarEnvios_debeRetornarLista() {

        Envio envio = new Envio();
        envio.setId(1L);
        envio.setPedidoId(10L);
        envio.setDestino("Santiago");
        envio.setTransportista("Chilexpress");
        envio.setNumeroTracking("TRK-123");
        envio.setEstado(EstadoEnvio.ASIGNADO);

        when(envioRepository.findAll())
                .thenReturn(List.of(envio));

        List<EnvioResponse> lista = envioService.listarEnvios();

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals(1L, lista.get(0).getId());
        assertEquals("Santiago", lista.get(0).getDestino());

        verify(envioRepository).findAll();
    }

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

        assertEquals(
                "Envio no encontrado: 99",
                exception.getMessage()
        );

        verify(envioRepository).findById(99L);
    }

    @Test
    void obtenerPorPedido_existente() {

        Envio envio = new Envio();
        envio.setId(1L);
        envio.setPedidoId(100L);
        envio.setDestino("Concepcion");
        envio.setTransportista("Bluex");
        envio.setNumeroTracking("TRK-111");
        envio.setEstado(EstadoEnvio.EN_RUTA);

        when(envioRepository.findByPedidoId(100L))
                .thenReturn(Optional.of(envio));

        EnvioResponse response =
                envioService.obtenerPorPedido(100L);

        assertNotNull(response);
        assertEquals(100L, response.getPedidoId());
        assertEquals("Concepcion", response.getDestino());
        assertEquals("EN_RUTA", response.getEstado());

        verify(envioRepository).findByPedidoId(100L);
    }

    @Test
    void obtenerPorPedido_inexistente() {

        when(envioRepository.findByPedidoId(500L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> envioService.obtenerPorPedido(500L)
        );

        assertEquals(
                "Envio no encontrado para pedido: 500",
                exception.getMessage()
        );

        verify(envioRepository).findByPedidoId(500L);
    }

    @Test
    void actualizarEstado_debeGuardarNuevoEstado() {

        Envio envio = new Envio();
        envio.setId(1L);
        envio.setPedidoId(10L);
        envio.setDestino("Santiago");
        envio.setTransportista("Chilexpress");
        envio.setNumeroTracking("TRK-123");
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

    @Test
    void actualizarEstado_conEstadoInvalido_debeLanzarExcepcion() {

        Envio envio = new Envio();
        envio.setId(1L);
        envio.setEstado(EstadoEnvio.ASIGNADO);

        when(envioRepository.findById(1L))
                .thenReturn(Optional.of(envio));

        assertThrows(
                IllegalArgumentException.class,
                () -> envioService.actualizarEstado(1L, "ESTADO_INVALIDO")
        );

        verify(envioRepository).findById(1L);
        verify(envioRepository, never()).save(any());
    }

}