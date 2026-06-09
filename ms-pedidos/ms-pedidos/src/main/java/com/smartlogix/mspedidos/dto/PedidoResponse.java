package com.smartlogix.mspedidos.dto;

public class PedidoResponse {

    private Long id;
    private Long usuarioId;
    private Long productoId;
    private Integer cantidad;
    private Double montoTotal;
    private String estado;
    private String destino;
    private String observaciones;

    public PedidoResponse(
            Long id,
            Long usuarioId,
            Long productoId,
            Integer cantidad,
            Double montoTotal,
            String estado,
            String destino,
            String observaciones) {

        this.id = id;
        this.usuarioId = usuarioId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.montoTotal = montoTotal;
        this.estado = estado;
        this.destino = destino;
        this.observaciones = observaciones;
    }

    public Long getId() { return id; }
    public Long getUsuarioId() { return usuarioId; }
    public Long getProductoId() { return productoId; }
    public Integer getCantidad() { return cantidad; }
    public Double getMontoTotal() { return montoTotal; }
    public String getEstado() { return estado; }
    public String getDestino() { return destino; }
    public String getObservaciones() { return observaciones; }
}
