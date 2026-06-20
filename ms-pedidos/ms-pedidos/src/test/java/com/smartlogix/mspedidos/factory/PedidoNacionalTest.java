package com.smartlogix.mspedidos.factory;

import com.smartlogix.mspedidos.model.Pedido;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoNacionalTest {

    @Test
    void aplicarReglas() {

        Pedido pedido = new Pedido();
        pedido.setMontoTotal(1000.0);

        PedidoNacional nacional = new PedidoNacional(pedido);

        nacional.aplicarReglas();

        assertEquals(
                "Pedido nacional - envio en 2 a 5 dias habiles",
                pedido.getObservaciones()
        );

        assertEquals(1000.0, pedido.getMontoTotal());
    }

    @Test
    void getTipo() {

        Pedido pedido = new Pedido();

        PedidoNacional nacional = new PedidoNacional(pedido);

        assertEquals("NACIONAL", nacional.getTipo());
    }

    @Test
    void getPedido() {

        Pedido pedido = new Pedido();

        PedidoNacional nacional = new PedidoNacional(pedido);

        assertEquals(pedido, nacional.getPedido());
    }
}