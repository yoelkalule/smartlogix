package com.smartlogix.mspedidos.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SinDescuentoTest {

    @Test
    void calcular() {

        SinDescuento strategy = new SinDescuento();

        assertEquals(1000.0, strategy.calcular(1000.0));
    }

    @Test
    void getNombre() {

        SinDescuento strategy = new SinDescuento();

        assertEquals("SIN_DESCUENTO", strategy.getNombre());
    }
}