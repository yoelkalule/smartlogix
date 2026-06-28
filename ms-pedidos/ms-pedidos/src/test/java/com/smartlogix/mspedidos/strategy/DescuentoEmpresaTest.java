package com.smartlogix.mspedidos.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DescuentoEmpresaTest {

    @Test
    void calcular_debeAplicar25PorCiento() {

        DescuentoEmpresa strategy = new DescuentoEmpresa();
        assertEquals(750.0, strategy.calcular(1000.0));
    }

    @Test
    void getNombre_debeRetornarEmpresa() {

        DescuentoEmpresa strategy = new DescuentoEmpresa();
        assertEquals("EMPRESA", strategy.getNombre());
    }

    @Test
    void calcular_conMontoCero_debeRetornarCero() {

        DescuentoEmpresa strategy = new DescuentoEmpresa();
        assertEquals(0.0, strategy.calcular(0.0));
    }
}