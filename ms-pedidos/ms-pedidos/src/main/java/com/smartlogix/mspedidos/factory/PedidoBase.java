package com.smartlogix.mspedidos.factory;

import com.smartlogix.mspedidos.model.Pedido;

public abstract class PedidoBase {
    protected Pedido pedido;

    public PedidoBase(Pedido pedido) {
        this.pedido = pedido;
    }

    public abstract void aplicarReglas();
    public abstract String getTipo();

    public Pedido getPedido() {
        return pedido;
    }
}