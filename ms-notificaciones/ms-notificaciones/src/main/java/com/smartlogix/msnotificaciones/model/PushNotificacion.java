package com.smartlogix.msnotificaciones.model;

public class PushNotificacion extends Notificacion {
    public PushNotificacion(Long pedidoId, String mensaje) {
        super(pedidoId, mensaje, TipoNotificacion.PUSH);
    }

    @Override
    public void enviar() {
        System.out.println("[Factory-PUSH] Enviando push para pedido #" + pedidoId + " -> " + mensaje);
    }
}