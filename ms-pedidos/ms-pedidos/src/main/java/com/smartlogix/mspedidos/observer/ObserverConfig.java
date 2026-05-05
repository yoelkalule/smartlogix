package com.smartlogix.mspedidos.observer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ObserverConfig implements CommandLineRunner {

    private final EventManager eventManager;
    private final NotificacionObserver notificacionObserver;

    public ObserverConfig(EventManager eventManager,
                          NotificacionObserver notificacionObserver) {
        this.eventManager = eventManager;
        this.notificacionObserver = notificacionObserver;
    }

    @Override
    public void run(String... args) {
        eventManager.subscribe("PEDIDO_CREADO", notificacionObserver);
        System.out.println("✅ Observer configurado: NotificacionObserver suscrito a PEDIDO_CREADO");
    }
}