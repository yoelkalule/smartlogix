package com.smartlogix.mspedidos.strategy;

import org.springframework.stereotype.Component;

@Component
public class DescuentoStrategyFactory {

    private final SinDescuento sinDescuento;
    private final DescuentoCliente descuentoCliente;
    private final DescuentoEmpresa descuentoEmpresa;

    public DescuentoStrategyFactory(SinDescuento sinDescuento,
                                    DescuentoCliente descuentoCliente,
                                    DescuentoEmpresa descuentoEmpresa) {
        this.sinDescuento     = sinDescuento;
        this.descuentoCliente = descuentoCliente;
        this.descuentoEmpresa = descuentoEmpresa;
    }

    public DescuentoStrategy getStrategy(String tipoCliente) {
        return switch (tipoCliente.toUpperCase()) {
            case "CLIENTE" -> descuentoCliente;
            case "EMPRESA" -> descuentoEmpresa;
            default        -> sinDescuento;
        };
    }
}