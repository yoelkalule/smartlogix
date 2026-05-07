package com.smartlogix.mspedidos.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NotificacionesClient {

    private final RestTemplate restTemplate;

    public NotificacionesClient() {
        this.restTemplate = new RestTemplate();
    }

    @CircuitBreaker(name = "notificaciones", fallbackMethod = "fallbackNotificar")
    public String notificarPedido(Long pedidoId) {
        String url = "http://ms-notificaciones:8084/api/notificaciones/enviar?pedidoId=" + pedidoId;
        return restTemplate.postForObject(url, null, String.class);
    }

    public String fallbackNotificar(Long pedidoId, Exception ex) {
        System.out.println("[CircuitBreaker] ms-notificaciones no disponible para pedido #"
            + pedidoId + ". Motivo: " + ex.getMessage());
        System.out.println("[CircuitBreaker] Pedido #" + pedidoId
            + " queda en estado PENDIENTE de notificacion.");
        return "Notificacion pendiente - ms-notificaciones no disponible";
    }
}