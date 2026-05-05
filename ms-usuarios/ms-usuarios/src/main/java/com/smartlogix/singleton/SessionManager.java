package com.smartlogix.msusuarios.singleton;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PATRÓN SINGLETON — SessionManager
 *
 * @Component hace que Spring cree UNA SOLA instancia de esta clase
 * y la inyecte en cualquier lugar que la necesite.
 *
 * ConcurrentHashMap garantiza que sea seguro cuando varios usuarios
 * hacen login al mismo tiempo (thread-safe).
 *
 * NUNCA uses: new SessionManager() — siempre inyecta por constructor.
 */
@Component
public class SessionManager {

    // Una sola instancia de este Map en toda la aplicación
    // Key: token  |  Value: nombre del usuario
    private final Map<String, String> sesionesActivas = new ConcurrentHashMap<>();

    // Spring llama este constructor UNA SOLA VEZ al arrancar
    public SessionManager() {
        System.out.println("[Singleton] SessionManager creado — única instancia en la JVM");
    }

    /**
     * Crea una sesión para el usuario y devuelve su token único.
     */
    public String crearSesion(String nombreUsuario) {
        String token = UUID.randomUUID().toString();
        sesionesActivas.put(token, nombreUsuario);
        System.out.println("[Singleton] Sesión creada para: " + nombreUsuario
            + " | Token: " + token);
        System.out.println("[Singleton] Sesiones activas: " + sesionesActivas.size());
        return token;
    }

    /**
     * Valida si el token existe en el Map — consulta en memoria, muy rápido.
     */
    public boolean validarToken(String token) {
        return sesionesActivas.containsKey(token);
    }

    /**
     * Devuelve el nombre del usuario asociado al token.
     */
    public String getUsuario(String token) {
        return sesionesActivas.get(token);
    }

    /**
     * Cierra la sesión eliminando el token del Map.
     */
    public void cerrarSesion(String token) {
        String usuario = sesionesActivas.remove(token);
        System.out.println("[Singleton] Sesión cerrada para: " + usuario);
        System.out.println("[Singleton] Sesiones activas: " + sesionesActivas.size());
    }

    /**
     * Devuelve cuántas sesiones hay activas — útil para monitoreo.
     */
    public int totalSesiones() {
        return sesionesActivas.size();
    }
}