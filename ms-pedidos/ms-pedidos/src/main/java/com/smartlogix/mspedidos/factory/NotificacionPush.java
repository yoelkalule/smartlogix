package com.smartlogix.mspedidos.factory;

/**
 * FACTORY METHOD — Producto Concreto #3
 */
public class NotificacionPush implements Notificacion {
    @Override
    public void enviar(Long pedidoId) {
        System.out.println("[Factory-Push] Enviando PUSH para pedido #" + pedidoId);
    }
}