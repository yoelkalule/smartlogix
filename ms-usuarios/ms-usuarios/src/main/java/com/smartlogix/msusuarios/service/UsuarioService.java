package com.smartlogix.msusuarios.service;

import com.smartlogix.msusuarios.dto.LoginRequest;
import com.smartlogix.msusuarios.dto.LoginResponse;
import com.smartlogix.msusuarios.model.Rol;
import com.smartlogix.msusuarios.model.Usuario;
import com.smartlogix.msusuarios.repository.UsuarioRepository;
import com.smartlogix.msusuarios.singleton.SessionManager;
import org.springframework.stereotype.Service;
import com.smartlogix.msusuarios.dto.UsuarioRegistroRequest;
import com.smartlogix.msusuarios.dto.UsuarioResponse;

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

    public UsuarioResponse registrar(UsuarioRegistroRequest request) {
    if (usuarioRepository.existsByEmail(request.getEmail())) {
        throw new RuntimeException("Email ya registrado");
    }

    Usuario usuario = new Usuario();
    usuario.setEmail(request.getEmail());
    usuario.setPassword(request.getPassword());
    usuario.setNombre(request.getNombre());
    usuario.setRol(request.getRol());

    Usuario guardado = usuarioRepository.save(usuario);

    return new UsuarioResponse(
        guardado.getId(),
        guardado.getEmail(),
        guardado.getNombre(),
        guardado.getRol().name()
    );
}
}