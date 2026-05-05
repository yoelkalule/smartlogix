package com.smartlogix.mspedidos.observer; 

public interface PedidoListener {
    void update(Long pedidoId, String estado);
}