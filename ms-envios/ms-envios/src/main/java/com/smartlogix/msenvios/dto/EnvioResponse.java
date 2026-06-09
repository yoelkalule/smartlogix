package com.smartlogix.msenvios.dto;

public class EnvioResponse {

    private Long id;
    private Long pedidoId;
    private String destino;
    private String transportista;
    private String numeroTracking;
    private String estado;

    public EnvioResponse(Long id,
                         Long pedidoId,
                         String destino,
                         String transportista,
                         String numeroTracking,
                         String estado) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.destino = destino;
        this.transportista = transportista;
        this.numeroTracking = numeroTracking;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public String getDestino() {
        return destino;
    }

    public String getTransportista() {
        return transportista;
    }

    public String getNumeroTracking() {
        return numeroTracking;
    }

    public String getEstado() {
        return estado;
    }
}