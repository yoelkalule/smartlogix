package com.smartlogix.msnotificaciones.dto;

import java.time.LocalDateTime;

public class NotificacionResponse {

    private Long id;
    private Long pedidoId;
    private String tipo;
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private Boolean enviado;

    public NotificacionResponse(Long id,
                                Long pedidoId,
                                String tipo,
                                String mensaje,
                                LocalDateTime fechaEnvio,
                                Boolean enviado) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.fechaEnvio = fechaEnvio;
        this.enviado = enviado;
    }

    public Long getId() {
        return id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public Boolean getEnviado() {
        return enviado;
    }
}