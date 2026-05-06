package com.smartlogix.msusuarios.singleton;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private final Map<String, String> sesionesActivas = new ConcurrentHashMap<>();

    public String crearSesion(String nombreUsuario) {
        String token = UUID.randomUUID().toString();
        sesionesActivas.put(token, nombreUsuario);
        return token;
    }

    public boolean validarToken(String token) {
        return sesionesActivas.containsKey(token);
    }

    public String getUsuario(String token) {
        return sesionesActivas.get(token);
    }

    public void cerrarSesion(String token) {
        sesionesActivas.remove(token);
    }

    public int totalSesiones() {
        return sesionesActivas.size();
    }
}