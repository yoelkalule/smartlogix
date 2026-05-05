package com.smartlogix.mspedidos.strategy;

import org.springframework.stereotype.Component;

@Component
public class DescuentoCliente implements DescuentoStrategy {

    private static final double PORCENTAJE = 0.10;

    @Override
    public double calcular(double montoOriginal) {
        double descuento = montoOriginal * PORCENTAJE;
        System.out.println("[Strategy-CLIENTE] Descuento 10%: -$" + descuento);
        return montoOriginal - descuento;
    }

    @Override
    public String getNombre() { return "CLIENTE"; }
}