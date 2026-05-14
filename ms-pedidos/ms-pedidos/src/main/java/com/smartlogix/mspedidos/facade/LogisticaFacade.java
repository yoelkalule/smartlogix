package com.smartlogix.mspedidos.facade;

import com.smartlogix.mspedidos.client.NotificacionesClient;
import com.smartlogix.mspedidos.dto.PedidoRequest;
import com.smartlogix.mspedidos.factory.PedidoBase;
import com.smartlogix.mspedidos.factory.PedidoFactory;
import com.smartlogix.mspedidos.model.EstadoPedido;
import com.smartlogix.mspedidos.model.Pedido;
import com.smartlogix.mspedidos.model.TipoPedido;
import com.smartlogix.mspedidos.observer.EventManager;
import com.smartlogix.mspedidos.repository.PedidoRepository;
import com.smartlogix.mspedidos.saga.PedidoSagaOrchestrator;
import com.smartlogix.mspedidos.strategy.DescuentoStrategy;
import com.smartlogix.mspedidos.strategy.DescuentoStrategyFactory;
import org.springframework.stereotype.Component;

@Component
public class LogisticaFacade {

    private final PedidoRepository pedidoRepository;
    private final PedidoFactory pedidoFactory;
    private final DescuentoStrategyFactory strategyFactory;
    private final EventManager eventManager;
    private final NotificacionesClient notificacionesClient;
    private final PedidoSagaOrchestrator sagaOrchestrator;

    public LogisticaFacade(PedidoRepository pedidoRepository,
                           PedidoFactory pedidoFactory,
                           DescuentoStrategyFactory strategyFactory,
                           EventManager eventManager,
                           NotificacionesClient notificacionesClient,
                           PedidoSagaOrchestrator sagaOrchestrator) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoFactory = pedidoFactory;
        this.strategyFactory = strategyFactory;
        this.eventManager = eventManager;
        this.notificacionesClient = notificacionesClient;
        this.sagaOrchestrator = sagaOrchestrator;
    }

    public Pedido procesarPedido(PedidoRequest request, String tipoCliente) {
        System.out.println("[Facade] Iniciando procesamiento de pedido...");

        Pedido pedido = new Pedido();
        pedido.setUsuarioId(request.getUsuarioId());
        pedido.setProductoId(request.getProductoId());
        pedido.setCantidad(request.getCantidad());
        pedido.setMontoTotal(request.getMontoTotal());
        pedido.setTipo(TipoPedido.valueOf(request.getTipo().toUpperCase()));
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setDestino(request.getDestino());

        System.out.println("[Facade] Paso 1: Aplicando Strategy de descuento para: " + tipoCliente);
        DescuentoStrategy strategy = strategyFactory.getStrategy(tipoCliente);
        pedido.setMontoTotal(strategy.calcular(pedido.getMontoTotal()));

        System.out.println("[Facade] Paso 2: Aplicando Factory Method para tipo: " + request.getTipo());
        PedidoBase pedidoBase = pedidoFactory.crear(request.getTipo(), pedido);
        pedidoBase.aplicarReglas();

        System.out.println("[Facade] Paso 3: Guardando pedido en BD...");
        Pedido guardado = pedidoRepository.save(pedido);

        System.out.println("[Facade] Paso 4: Disparando Observer PEDIDO_CREADO...");
        eventManager.notify("PEDIDO_CREADO", guardado.getId(), "CREADO");

        System.out.println("[Facade] Paso 5: Ejecutando SAGA...");
        guardado = sagaOrchestrator.ejecutar(guardado, request);

        System.out.println("[Facade] Paso 6: Notificando via Circuit Breaker...");
        notificacionesClient.notificarPedido(guardado.getId());

        System.out.println("[Facade] Pedido #" + guardado.getId() + " procesado.");
        return guardado;
    }
}