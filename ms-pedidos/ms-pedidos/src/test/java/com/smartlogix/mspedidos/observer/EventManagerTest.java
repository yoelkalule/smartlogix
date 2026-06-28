package com.smartlogix.mspedidos.observer;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class EventManagerTest {

    @Test
    void subscribe_y_notify_debeEjecutarUpdate() {

        EventManager manager = new EventManager();
        PedidoListener listener = mock(PedidoListener.class);

        manager.subscribe("PEDIDO_CREADO", listener);
        manager.notify("PEDIDO_CREADO", 1L, "CREADO");

        verify(listener).update(1L, "CREADO");
    }

    @Test
    void notify_sinListeners_noDebeLanzarExcepcion() {

        EventManager manager = new EventManager();
        manager.notify("EVENTO_SIN_LISTENERS", 1L, "CREADO");
    }

    @Test
    void subscribe_multipleListeners_debeNotificarATodos() {

        EventManager manager = new EventManager();
        PedidoListener listener1 = mock(PedidoListener.class);
        PedidoListener listener2 = mock(PedidoListener.class);

        manager.subscribe("PEDIDO_CREADO", listener1);
        manager.subscribe("PEDIDO_CREADO", listener2);
        manager.notify("PEDIDO_CREADO", 5L, "CREADO");

        verify(listener1).update(5L, "CREADO");
        verify(listener2).update(5L, "CREADO");
    }

    @Test
    void notify_eventoDistinto_noDebeEjecutarListener() {

        EventManager manager = new EventManager();
        PedidoListener listener = mock(PedidoListener.class);

        manager.subscribe("PEDIDO_CREADO", listener);
        manager.notify("PEDIDO_CANCELADO", 1L, "CANCELADO");

        verify(listener, never()).update(anyLong(), anyString());
    }
}