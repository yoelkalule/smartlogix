package com.smartlogix.msnotificaciones.model;

public class EmailNotificacion extends Notificacion {
    public EmailNotificacion(Long pedidoId, String mensaje) {
        super(pedidoId, mensaje, TipoNotificacion.EMAIL);
    }

    @Override
    public void enviar() {
        System.out.println("[Factory-EMAIL] Enviando email para pedido #" + pedidoId + " -> " + mensaje);
    }
}