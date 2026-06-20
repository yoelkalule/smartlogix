package com.smartlogix.mspedidos.observer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class NotificacionObserverTest {

    @Test
    void updateNoDebeLanzarExcepcion() {

        NotificacionObserver observer =
                new NotificacionObserver();

        assertDoesNotThrow(() ->
                observer.update(1L, "CREADO"));
    }
}