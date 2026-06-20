package com.smartlogix.mspedidos.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DescuentoStrategyFactoryTest {

    private final SinDescuento sin = new SinDescuento();
    private final DescuentoCliente cliente = new DescuentoCliente();
    private final DescuentoEmpresa empresa = new DescuentoEmpresa();

    private final DescuentoStrategyFactory factory =
            new DescuentoStrategyFactory(
                    sin,
                    cliente,
                    empresa
            );

    @Test
    void cliente() {

        assertTrue(factory.getStrategy("CLIENTE")
                instanceof DescuentoCliente);
    }

    @Test
    void empresa() {

        assertTrue(factory.getStrategy("EMPRESA")
                instanceof DescuentoEmpresa);
    }

    @Test
    void defaultStrategy() {

        assertTrue(factory.getStrategy("OTRO")
                instanceof SinDescuento);
    }

    @Test
    void minusculas() {

        assertTrue(factory.getStrategy("cliente")
                instanceof DescuentoCliente);
    }
}