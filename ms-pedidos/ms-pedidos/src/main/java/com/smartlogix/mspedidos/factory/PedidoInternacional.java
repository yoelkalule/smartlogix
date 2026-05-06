package com.smartlogix.mspedidos.factory;

import com.smartlogix.mspedidos.model.Pedido;

public class PedidoInternacional extends PedidoBase {

    public PedidoInternacional(Pedido pedido) {
        super(pedido);
    }

    @Override
    public void aplicarReglas() {
        pedido.setMontoTotal(pedido.getMontoTotal() * 1.19);
        pedido.setObservaciones("Pedido internacional - IVA aplicado, envio en 10 a 20 dias habiles");
    }

    @Override
    public String getTipo() {
        return "INTERNACIONAL";
    }
}