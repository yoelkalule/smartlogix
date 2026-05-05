package com.smartlogix.mspedidos.strategy;

import org.springframework.stereotype.Component;

@Component
public class DescuentoEmpresa implements DescuentoStrategy {

    private static final double PORCENTAJE = 0.25;

    @Override
    public double calcular(double montoOriginal) {
        double descuento = montoOriginal * PORCENTAJE;
        System.out.println("[Strategy-EMPRESA] Descuento 25%: -$" + descuento);
        return montoOriginal - descuento;
    }

    @Override
    public String getNombre() { return "EMPRESA"; }
}