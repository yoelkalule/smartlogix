package com.smartlogix.msusuarios.service;

import com.smartlogix.msusuarios.dto.LoginRequest;
import com.smartlogix.msusuarios.dto.LoginResponse;
import com.smartlogix.msusuarios.model.Rol;
import com.smartlogix.msusuarios.model.Usuario;
import com.smartlogix.msusuarios.repository.UsuarioRepository;
import com.smartlogix.msusuarios.singleton.SessionManager;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SessionManager sessionManager;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          SessionManager sessionManager) {
        this.usuarioRepository = usuarioRepository;
        this.sessionManager = sessionManager;
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Credenciales invalidas"));

        if (!request.getPassword().equals(usuario.getPassword())) {
            throw new RuntimeException("Credenciales invalidas");
        }

        String token = sessionManager.crearSesion(usuario.getNombre());

        return new LoginResponse(
            token,
            usuario.getNombre(),
            usuario.getEmail(),
            usuario.getRol().name()
        );
    }

    public Usuario registrar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email ya registrado");
        }
        return usuarioRepository.save(usuario);
    }
}