package com.smartlogix.mspedidos.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DescuentoClienteTest {

    @Test
    void calcular() {

        DescuentoCliente strategy = new DescuentoCliente();

        assertEquals(900.0, strategy.calcular(1000.0));
    }

    @Test
    void getNombre() {

        DescuentoCliente strategy = new DescuentoCliente();

        assertEquals("CLIENTE", strategy.getNombre());
    }
}