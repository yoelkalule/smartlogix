package com.smartlogix.mspedidos.factory;

/**
 * FACTORY METHOD — Producto Concreto #1
 */
public class NotificacionEmail implements Notificacion {
    @Override
    public void enviar(Long pedidoId) {
        System.out.println("[Factory-Email] Enviando EMAIL para pedido #" + pedidoId);
    }
}