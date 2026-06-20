package com.smartlogix.mspedidos.factory;

import com.smartlogix.mspedidos.model.Pedido;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoFactoryTest {

    private final PedidoFactory factory =
            new PedidoFactory();

    @Test
    void crearNacional() {

        PedidoBase pedido =
                factory.crear("NACIONAL", new Pedido());

        assertTrue(pedido instanceof PedidoNacional);
    }

    @Test
    void crearInternacional() {

        PedidoBase pedido =
                factory.crear("INTERNACIONAL", new Pedido());

        assertTrue(pedido instanceof PedidoInternacional);
    }

    @Test
    void crearConMinusculas() {

        PedidoBase pedido =
                factory.crear("nacional", new Pedido());

        assertTrue(pedido instanceof PedidoNacional);
    }

    @Test
    void tipoInvalido() {

        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> factory.crear("ABC", new Pedido())
                );

        assertEquals(
                "Tipo de pedido desconocido: ABC",
                ex.getMessage()
        );
    }
}