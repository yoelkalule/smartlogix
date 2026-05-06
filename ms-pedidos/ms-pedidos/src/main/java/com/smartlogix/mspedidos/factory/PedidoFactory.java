package com.smartlogix.mspedidos.factory;

import com.smartlogix.mspedidos.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoFactory {

    public PedidoBase crear(String tipo, Pedido pedido) {
        return switch (tipo.toUpperCase()) {
            case "NACIONAL" -> new PedidoNacional(pedido);
            case "INTERNACIONAL" -> new PedidoInternacional(pedido);
            default -> throw new IllegalArgumentException("Tipo de pedido desconocido: " + tipo);
        };
    }
}