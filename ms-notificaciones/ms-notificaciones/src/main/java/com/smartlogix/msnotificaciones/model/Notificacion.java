package com.smartlogix.msnotificaciones.model;

public abstract class Notificacion {
    protected Long pedidoId;
    protected String mensaje;
    protected TipoNotificacion tipo;

    public Notificacion(Long pedidoId, String mensaje, TipoNotificacion tipo) {
        this.pedidoId = pedidoId;
        this.mensaje  = mensaje;
        this.tipo     = tipo;
    }

    public abstract void enviar();
    public TipoNotificacion getTipo() { return tipo; }
}