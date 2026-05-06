package com.smartlogix.msusuarios.controller;

import com.smartlogix.msusuarios.dto.LoginRequest;
import com.smartlogix.msusuarios.dto.LoginResponse;
import com.smartlogix.msusuarios.model.Rol;
import com.smartlogix.msusuarios.model.Usuario;
import com.smartlogix.msusuarios.service.UsuarioService;
import com.smartlogix.msusuarios.singleton.SessionManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final SessionManager sessionManager;

    public UsuarioController(UsuarioService usuarioService,
                             SessionManager sessionManager) {
        this.usuarioService = usuarioService;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/test")
    public String test() {
        return "ms-usuarios funcionando";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(usuarioService.login(request));
    }

    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.registrar(usuario));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam String token) {
        sessionManager.cerrarSesion(token);
        return ResponseEntity.ok("Sesion cerrada");
    }

    @GetMapping("/validar")
    public ResponseEntity<Boolean> validar(@RequestParam String token) {
        return ResponseEntity.ok(sessionManager.validarToken(token));
    }

    @GetMapping("/sesiones")
    public ResponseEntity<Integer> sesiones() {
        return ResponseEntity.ok(sessionManager.totalSesiones());
    }
}