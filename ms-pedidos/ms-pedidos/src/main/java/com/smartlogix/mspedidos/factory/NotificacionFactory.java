package com.smartlogix.mspedidos.factory;

import org.springframework.stereotype.Component;

/**
 * PATRÓN FACTORY METHOD — La Fábrica
 * Decide qué tipo de notificación crear según el tipo de evento.
 * El controller no sabe qué clase concreta se instancia — solo pide al factory.
 */
@Component
public class NotificacionFactory {

    public Notificacion crear(String tipoEvento) {
        return switch (tipoEvento.toUpperCase()) {
            case "EMAIL"  -> new NotificacionEmail();
            case "SMS"    -> new NotificacionSMS();
            case "PUSH"   -> new NotificacionPush();
            default -> throw new IllegalArgumentException(
                "[Factory] Tipo de notificación desconocido: " + tipoEvento);
        };
    }
}