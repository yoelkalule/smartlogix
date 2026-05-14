package com.smartlogix.mspedidos.saga;

import com.smartlogix.mspedidos.dto.PedidoRequest;
import com.smartlogix.mspedidos.model.EstadoPedido;
import com.smartlogix.mspedidos.model.Pedido;
import com.smartlogix.mspedidos.repository.PedidoRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PedidoSagaOrchestrator {

    private final PedidoRepository pedidoRepository;
    private final RestTemplate restTemplate;

    public PedidoSagaOrchestrator(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.restTemplate = new RestTemplate();
    }

    public Pedido ejecutar(Pedido pedido, PedidoRequest request) {
        System.out.println("[SAGA] Iniciando orquestacion para pedido #" + pedido.getId());

        boolean stockDescontado = false;

        try {
            System.out.println("[SAGA] Paso 1: Pedido creado correctamente ID: " + pedido.getId());

            System.out.println("[SAGA] Paso 2: Descontando stock del producto #" + request.getProductoId());
            String urlStock = "http://ms-inventario:8081/api/inventario/productos/"
                + request.getProductoId() + "/stock?cantidad=" + request.getCantidad();
            restTemplate.put(urlStock, null);
            stockDescontado = true;
            System.out.println("[SAGA] Paso 2: Stock descontado correctamente");

            System.out.println("[SAGA] Paso 3: Creando envio para pedido #" + pedido.getId());
            String urlEnvio = "http://ms-envios:8083/api/envios";
            String body = "{\"pedidoId\":" + pedido.getId()
                + ",\"destino\":\"" + pedido.getDestino()
                + "\",\"transportista\":\"Asignado automaticamente\"}";

            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("Content-Type", "application/json");
            org.springframework.http.HttpEntity<String> entity =
                new org.springframework.http.HttpEntity<>(body, headers);

            restTemplate.postForObject(urlEnvio, entity, String.class);
            System.out.println("[SAGA] Paso 3: Envio creado correctamente");

            pedido.setEstado(EstadoPedido.CONFIRMADO);
            pedidoRepository.save(pedido);
            System.out.println("[SAGA] Pedido #" + pedido.getId() + " completado exitosamente");

        } catch (Exception e) {
            System.out.println("[SAGA] Error en paso: " + e.getMessage());
            System.out.println("[SAGA] Iniciando compensacion...");

            if (stockDescontado) {
                try {
                    System.out.println("[SAGA] Compensacion: Devolviendo stock del producto #"
                        + request.getProductoId());
                    String urlDevolver = "http://ms-inventario:8081/api/inventario/productos/"
                        + request.getProductoId() + "/stock/devolver?cantidad=" + request.getCantidad();
                    restTemplate.put(urlDevolver, null);
                    System.out.println("[SAGA] Compensacion: Stock devuelto correctamente");
                } catch (Exception ex) {
                    System.out.println("[SAGA] Error al devolver stock: " + ex.getMessage());
                }
            }

            pedido.setEstado(EstadoPedido.CANCELADO);
            pedido.setObservaciones("SAGA: " + e.getMessage());
            pedidoRepository.save(pedido);
            System.out.println("[SAGA] Compensacion: Pedido #" + pedido.getId() + " cancelado");
        }

        return pedido;
    }
}