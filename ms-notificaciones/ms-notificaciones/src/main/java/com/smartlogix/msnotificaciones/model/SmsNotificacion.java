package com.smartlogix.msnotificaciones.model;

public class SmsNotificacion extends Notificacion {
    public SmsNotificacion(Long pedidoId, String mensaje) {
        super(pedidoId, mensaje, TipoNotificacion.SMS);
    }

    @Override
    public void enviar() {
        System.out.println("[Factory-SMS] Enviando SMS para pedido #" + pedidoId + " -> " + mensaje);
    }
}