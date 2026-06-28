package com.smartlogix.msenvios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EnvioRequest {

    @NotNull(message = "El pedidoId es obligatorio")
    private Long pedidoId;

    @NotBlank(message = "El destino es obligatorio")
    private String destino;

    @NotBlank(message = "El transportista es obligatorio")
    private String transportista;

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }
    public String getTransportista() { return transportista; }
    public void setTransportista(String transportista) { this.transportista = transportista; }
}