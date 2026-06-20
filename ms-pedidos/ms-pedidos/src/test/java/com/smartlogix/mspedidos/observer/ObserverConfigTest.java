package com.smartlogix.mspedidos.observer;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ObserverConfigTest {

    @Test
    void runDebeRegistrarObserver() throws Exception {

        EventManager manager = mock(EventManager.class);

        NotificacionObserver observer = mock(NotificacionObserver.class);

        ObserverConfig config =
                new ObserverConfig(manager, observer);

        config.run();

        verify(manager)
                .subscribe("PEDIDO_CREADO", observer);
    }
}