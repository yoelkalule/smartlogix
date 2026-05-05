package com.smartlogix.mspedidos.observer;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class EventManager {
    private Map<String, List<PedidoListener>> listeners = new HashMap<>();

    public void subscribe(String eventType, PedidoListener listener) {
        listeners.putIfAbsent(eventType, new ArrayList<>());
        listeners.get(eventType).add(listener);
    }

    public void notify(String eventType, Long pedidoId, String estado) {
        List<PedidoListener> users = listeners.get(eventType);
        if (users != null) {
            for (PedidoListener listener : users) {
                listener.update(pedidoId, estado);
            }
        }
    }
}