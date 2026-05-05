package com.smartlogix.mspedidos.factory;

/**
 * FACTORY METHOD — Producto Concreto #2
 */
public class NotificacionSMS implements Notificacion {
    @Override
    public void enviar(Long pedidoId) {
        System.out.println("[Factory-SMS] Enviando SMS para pedido #" + pedidoId);
    }
}