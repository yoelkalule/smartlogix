package com.smartlogix.mspedidos.observer;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NotificacionObserver implements PedidoListener {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void update(Long pedidoId, String estado) {
        System.out.println("Patrón Observer: Se detectó la creación del pedido ID " + pedidoId);
        System.out.println("Avisando a ms-notificaciones vía HTTP...");

        try {
            String url = "http://ms-notificaciones:8084/api/notificaciones/enviar?pedidoId=" + pedidoId;
            restTemplate.postForObject(url, null, String.class);
            System.out.println("Notificación enviada con éxito");
        } catch (Exception e) {
            System.out.println("Error al conectar con ms-notificaciones: " + e.getMessage());
        }
    }
}