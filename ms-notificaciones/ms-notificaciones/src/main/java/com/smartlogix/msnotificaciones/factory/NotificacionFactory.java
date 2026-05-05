package com.smartlogix.msnotificaciones.factory;

import com.smartlogix.msnotificaciones.model.*;
import org.springframework.stereotype.Component;

@Component
public class NotificacionFactory {

    public Notificacion crear(String tipo, Long pedidoId, String mensaje) {
        return switch (tipo.toUpperCase()) {
            case "EMAIL" -> new EmailNotificacion(pedidoId, mensaje);
            case "SMS"   -> new SmsNotificacion(pedidoId, mensaje);
            case "PUSH"  -> new PushNotificacion(pedidoId, mensaje);
            default -> throw new IllegalArgumentException("Tipo desconocido: " + tipo);
        };
    }
}