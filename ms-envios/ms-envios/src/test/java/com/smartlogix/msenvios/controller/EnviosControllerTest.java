package com.smartlogix.msenvios.controller;

import com.smartlogix.msenvios.dto.EnvioRequest;
import com.smartlogix.msenvios.dto.EnvioResponse;
import com.smartlogix.msenvios.service.EnvioService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EnviosControllerTest {

    @Test
    void test() {

        EnvioService service = mock(EnvioService.class);

        EnviosController controller =
                new EnviosController(service);

        assertEquals(
                "ms-envios funcionando",
                controller.test()
        );
    }

    @Test
    void crear() {

        EnvioService service = mock(EnvioService.class);

        EnvioRequest request = new EnvioRequest();

        EnvioResponse response =
                new EnvioResponse(
                        1L,
                        100L,
                        "Santiago",
                        "Chilexpress",
                        "TRK123",
                        "ASIGNADO"
                );

        when(service.crearEnvio(request))
                .thenReturn(response);

        EnviosController controller =
                new EnviosController(service);

        assertEquals(
                response,
                controller.crear(request).getBody()
        );

        verify(service).crearEnvio(request);
    }

    @Test
    void listar() {

        EnvioService service = mock(EnvioService.class);

        List<EnvioResponse> lista = List.of(
                new EnvioResponse(
                        1L,
                        100L,
                        "Santiago",
                        "Chilexpress",
                        "TRK123",
                        "ASIGNADO"
                )
        );

        when(service.listarEnvios())
                .thenReturn(lista);

        EnviosController controller =
                new EnviosController(service);

        assertEquals(
                lista,
                controller.listar().getBody()
        );

        verify(service).listarEnvios();
    }

    @Test
    void obtener() {

        EnvioService service = mock(EnvioService.class);

        EnvioResponse response =
                new EnvioResponse(
                        1L,
                        100L,
                        "Santiago",
                        "Chilexpress",
                        "TRK123",
                        "ASIGNADO"
                );

        when(service.obtenerEnvio(1L))
                .thenReturn(response);

        EnviosController controller =
                new EnviosController(service);

        assertEquals(
                response,
                controller.obtener(1L).getBody()
        );

        verify(service).obtenerEnvio(1L);
    }

    @Test
    void obtenerPorPedido() {

        EnvioService service = mock(EnvioService.class);

        EnvioResponse response =
                new EnvioResponse(
                        1L,
                        100L,
                        "Santiago",
                        "Chilexpress",
                        "TRK123",
                        "ASIGNADO"
                );

        when(service.obtenerPorPedido(100L))
                .thenReturn(response);

        EnviosController controller =
                new EnviosController(service);

        assertEquals(
                response,
                controller.obtenerPorPedido(100L).getBody()
        );

        verify(service).obtenerPorPedido(100L);
    }

    @Test
    void actualizarEstado() {

        EnvioService service = mock(EnvioService.class);

        EnvioResponse response =
                new EnvioResponse(
                        1L,
                        100L,
                        "Santiago",
                        "Chilexpress",
                        "TRK123",
                        "EN_TRANSITO"
                );

        when(service.actualizarEstado(1L, "EN_TRANSITO"))
                .thenReturn(response);

        EnviosController controller =
                new EnviosController(service);

        assertEquals(
                response,
                controller.actualizarEstado(
                        1L,
                        "EN_TRANSITO"
                ).getBody()
        );

        verify(service).actualizarEstado(
                1L,
                "EN_TRANSITO"
        );
    }
}