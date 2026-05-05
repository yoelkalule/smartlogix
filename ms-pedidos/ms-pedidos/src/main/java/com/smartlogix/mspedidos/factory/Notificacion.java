package com.smartlogix.mspedidos.factory;

/**
 * PATRÓN FACTORY METHOD — Producto (interfaz base)
 * Todos los tipos de notificación implementan esta interfaz.
 * El Factory decide cuál crear sin que el resto del código lo sepa.
 */
public interface Notificacion {
    void enviar(Long pedidoId);
}