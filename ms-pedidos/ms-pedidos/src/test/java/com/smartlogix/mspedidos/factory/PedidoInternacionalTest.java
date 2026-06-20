package com.smartlogix.mspedidos.factory;

import com.smartlogix.mspedidos.model.Pedido;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoInternacionalTest {

    @Test
    void aplicarReglas() {

        Pedido pedido = new Pedido();
        pedido.setMontoTotal(1000.0);

        PedidoInternacional internacional =
                new PedidoInternacional(pedido);

        internacional.aplicarReglas();

        assertEquals(1190.0, pedido.getMontoTotal());

        assertEquals(
                "Pedido internacional - IVA aplicado, envio en 10 a 20 dias habiles",
                pedido.getObservaciones()
        );
    }

    @Test
    void getTipo() {

        Pedido pedido = new Pedido();

        PedidoInternacional internacional =
                new PedidoInternacional(pedido);

        assertEquals(
                "INTERNACIONAL",
                internacional.getTipo()
        );
    }

    @Test
    void getPedido() {

        Pedido pedido = new Pedido();

        PedidoInternacional internacional =
                new PedidoInternacional(pedido);

        assertEquals(
                pedido,
                internacional.getPedido()
        );
    }
}