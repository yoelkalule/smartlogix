package com.smartlogix.msusuarios.controller;

import com.smartlogix.msusuarios.dto.LoginRequest;
import com.smartlogix.msusuarios.dto.LoginResponse;
import com.smartlogix.msusuarios.dto.UsuarioRegistroRequest;
import com.smartlogix.msusuarios.dto.UsuarioResponse;
import com.smartlogix.msusuarios.model.Rol;
import com.smartlogix.msusuarios.service.UsuarioService;
import com.smartlogix.msusuarios.singleton.SessionManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Test
    void testEndpoint() {

        UsuarioService service = mock(UsuarioService.class);
        SessionManager session = mock(SessionManager.class);

        UsuarioController controller =
                new UsuarioController(service, session);

        assertEquals(
                "ms-usuarios funcionando",
                controller.test()
        );
    }

    @Test
    void login() {

        UsuarioService service = mock(UsuarioService.class);
        SessionManager session = mock(SessionManager.class);

        LoginRequest request = new LoginRequest();
        request.setEmail("cliente@test.com");
        request.setPassword("1234");

        LoginResponse response =
                new LoginResponse(
                        "token123",
                        "Cliente",
                        "cliente@test.com",
                        "CLIENTE"
                );

        when(service.login(any(LoginRequest.class)))
                .thenReturn(response);

        UsuarioController controller =
                new UsuarioController(service, session);

        assertEquals(
                response,
                controller.login(request).getBody()
        );

        verify(service).login(request);
    }

    @Test
    void registrar() {

        UsuarioService service = mock(UsuarioService.class);
        SessionManager session = mock(SessionManager.class);

        UsuarioRegistroRequest request =
                new UsuarioRegistroRequest();

        request.setNombre("Juan");
        request.setEmail("juan@test.com");
        request.setPassword("1234");
        request.setRol(Rol.CLIENTE);

        UsuarioResponse response =
                new UsuarioResponse(
                        1L,
                        "juan@test.com",
                        "Juan",
                        "CLIENTE"
                );

        when(service.registrar(any()))
                .thenReturn(response);

        UsuarioController controller =
                new UsuarioController(service, session);

        assertEquals(
                response,
                controller.registrar(request).getBody()
        );

        verify(service).registrar(request);
    }

    @Test
    void validarSesion() {

        UsuarioService service = mock(UsuarioService.class);
        SessionManager session = mock(SessionManager.class);

        when(session.validarToken("abc"))
                .thenReturn(true);

        UsuarioController controller =
                new UsuarioController(service, session);

        assertTrue(controller.validar("abc").getBody());

        verify(session).validarToken("abc");
    }

    @Test
    void totalSesiones() {

        UsuarioService service = mock(UsuarioService.class);
        SessionManager session = mock(SessionManager.class);

        when(session.totalSesiones())
                .thenReturn(3);

        UsuarioController controller =
                new UsuarioController(service, session);

        assertEquals(
                3,
                controller.sesiones().getBody()
        );

        verify(session).totalSesiones();
    }

    @Test
    void logout() {

        UsuarioService service = mock(UsuarioService.class);
        SessionManager session = mock(SessionManager.class);

        UsuarioController controller =
                new UsuarioController(service, session);

        assertEquals(
                "Sesion cerrada",
                controller.logout("abc").getBody()
        );

        verify(session).cerrarSesion("abc");
    }
}