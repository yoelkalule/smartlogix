package com.smartlogix.msusuarios.service;

import com.smartlogix.msusuarios.dto.LoginRequest;
import com.smartlogix.msusuarios.dto.LoginResponse;
import com.smartlogix.msusuarios.model.Rol;
import com.smartlogix.msusuarios.model.Usuario;
import com.smartlogix.msusuarios.repository.UsuarioRepository;
import com.smartlogix.msusuarios.singleton.SessionManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private SessionManager sessionManager;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void login_conCredencialesValidas_debeRetornarLoginResponse() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("cliente@smartlogix.com");
        usuario.setPassword("cliente123");
        usuario.setNombre("Cliente Demo");
        usuario.setRol(Rol.CLIENTE);

        LoginRequest request = new LoginRequest();
        request.setEmail("cliente@smartlogix.com");
        request.setPassword("cliente123");

        when(usuarioRepository.findByEmail("cliente@smartlogix.com"))
                .thenReturn(Optional.of(usuario));

        when(sessionManager.crearSesion("Cliente Demo"))
                .thenReturn("token-test");

        LoginResponse response = usuarioService.login(request);

        assertEquals("token-test", response.getToken());
        assertEquals("Cliente Demo", response.getNombre());
        assertEquals("cliente@smartlogix.com", response.getEmail());
        assertEquals("CLIENTE", response.getRol());

        verify(usuarioRepository).findByEmail("cliente@smartlogix.com");
        verify(sessionManager).crearSesion("Cliente Demo");
    }

    @Test
    void login_conPasswordIncorrecta_debeLanzarExcepcion() {
        Usuario usuario = new Usuario();
        usuario.setEmail("cliente@smartlogix.com");
        usuario.setPassword("cliente123");
        usuario.setNombre("Cliente Demo");
        usuario.setRol(Rol.CLIENTE);

        LoginRequest request = new LoginRequest();
        request.setEmail("cliente@smartlogix.com");
        request.setPassword("incorrecta");

        when(usuarioRepository.findByEmail("cliente@smartlogix.com"))
                .thenReturn(Optional.of(usuario));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.login(request);
        });

        assertEquals("Credenciales invalidas", exception.getMessage());

        verify(usuarioRepository).findByEmail("cliente@smartlogix.com");
        verify(sessionManager, never()).crearSesion(anyString());
    }
}