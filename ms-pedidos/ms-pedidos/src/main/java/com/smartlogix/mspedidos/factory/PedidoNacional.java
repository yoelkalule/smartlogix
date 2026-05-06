package com.smartlogix.mspedidos.factory;

import com.smartlogix.mspedidos.model.Pedido;

public class PedidoNacional extends PedidoBase {

    public PedidoNacional(Pedido pedido) {
        super(pedido);
    }

    @Override
    public void aplicarReglas() {
        pedido.setObservaciones("Pedido nacional - envio en 2 a 5 dias habiles");
    }

    @Override
    public String getTipo() {
        return "NACIONAL";
    }
}