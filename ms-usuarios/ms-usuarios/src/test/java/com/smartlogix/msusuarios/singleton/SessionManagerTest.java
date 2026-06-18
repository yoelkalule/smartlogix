package com.smartlogix.msusuarios.singleton;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionManagerTest {

    @Test
    void crearValidarYCerrarSesion() {

        SessionManager manager = new SessionManager();

        String token = manager.crearSesion("aaron");

        assertNotNull(token);

        assertTrue(manager.validarToken(token));

        assertEquals("aaron", manager.getUsuario(token));

        assertEquals(1, manager.totalSesiones());

        manager.cerrarSesion(token);

        assertFalse(manager.validarToken(token));

        assertEquals(0, manager.totalSesiones());
    }
}