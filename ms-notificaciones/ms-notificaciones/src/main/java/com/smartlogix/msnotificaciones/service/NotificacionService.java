package com.smartlogix.msnotificaciones.service;

import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    // Ya no usamos @KafkaListener, ahora será llamado desde el controlador
    public void procesarEvento(Long pedidoId) {
        System.out.println("--------------------------------------------------");
        System.out.println("🔔 LOGICA OBSERVER: Se ha detectado un evento (Vía HTTP)");
        System.out.println("Mensaje recibido: Se creó el pedido ID " + pedidoId);
        System.out.println("Acción: Enviando correo al usuario...");
        System.out.println("--------------------------------------------------");
    }
}