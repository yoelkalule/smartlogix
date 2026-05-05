package com.smartlogix.mspedidos.strategy;

public interface DescuentoStrategy {
    double calcular(double montoOriginal);
    String getNombre();
}