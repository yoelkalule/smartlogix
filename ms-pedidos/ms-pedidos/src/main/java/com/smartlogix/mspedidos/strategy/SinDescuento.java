package com.smartlogix.mspedidos.strategy;

import org.springframework.stereotype.Component;

@Component
public class SinDescuento implements DescuentoStrategy {

    @Override
    public double calcular(double montoOriginal) {
        System.out.println("[Strategy-SIN_DESCUENTO] Sin descuento aplicado.");
        return montoOriginal;
    }

    @Override
    public String getNombre() { return "SIN_DESCUENTO"; }
}