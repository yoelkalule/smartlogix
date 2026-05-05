package com.smartlogix.msusuarios.controller;

import com.smartlogix.msusuarios.singleton.SessionManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class StatusController {

    // Spring inyecta el Singleton por constructor — siempre la misma instancia
    private final SessionManager sessionManager;

    public StatusController(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    // Endpoint original
    @GetMapping("/test")
    public String test() {
        return "¡Hola! El microservicio de Usuarios de SmartLogix está funcionando.";
    }

    // Login: crea una sesión en el Singleton y devuelve el token
    @PostMapping("/login")
    public String login(@RequestParam String usuario) {
        String token = sessionManager.crearSesion(usuario);
        return "Login exitoso. Token: " + token;
    }

    // Valida si un token existe en el Singleton
    @GetMapping("/validar")
    public String validar(@RequestParam String token) {
        if (sessionManager.validarToken(token)) {
            return "✅ Token válido. Usuario: " + sessionManager.getUsuario(token);
        }
        return "❌ Token inválido o sesión expirada.";
    }

    // Logout: elimina el token del Singleton
    @PostMapping("/logout")
    public String logout(@RequestParam String token) {
        if (sessionManager.validarToken(token)) {
            sessionManager.cerrarSesion(token);
            return "✅ Sesión cerrada correctamente.";
        }
        return "❌ Token no encontrado.";
    }

    // Muestra cuántas sesiones activas hay — prueba que es el mismo Singleton
    @GetMapping("/sesiones")
    public String sesiones() {
        return "Sesiones activas en el Singleton: " + sessionManager.totalSesiones();
    }
}