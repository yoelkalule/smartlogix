package com.smartlogix.mspedidos.service;

import com.smartlogix.mspedidos.dto.PedidoRequest;
import com.smartlogix.mspedidos.factory.PedidoBase;
import com.smartlogix.mspedidos.factory.PedidoFactory;
import com.smartlogix.mspedidos.model.EstadoPedido;
import com.smartlogix.mspedidos.model.Pedido;
import com.smartlogix.mspedidos.model.TipoPedido;
import com.smartlogix.mspedidos.observer.EventManager;
import com.smartlogix.mspedidos.repository.PedidoRepository;
import com.smartlogix.mspedidos.strategy.DescuentoStrategy;
import com.smartlogix.mspedidos.strategy.DescuentoStrategyFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoFactory pedidoFactory;
    private final DescuentoStrategyFactory strategyFactory;
    private final EventManager eventManager;

    public PedidoService(PedidoRepository pedidoRepository,
                         PedidoFactory pedidoFactory,
                         DescuentoStrategyFactory strategyFactory,
                         EventManager eventManager) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoFactory = pedidoFactory;
        this.strategyFactory = strategyFactory;
        this.eventManager = eventManager;
    }

    public Pedido crearPedido(PedidoRequest request, String tipoCliente) {
        Pedido pedido = new Pedido();
        pedido.setUsuarioId(request.getUsuarioId());
        pedido.setProductoId(request.getProductoId());
        pedido.setCantidad(request.getCantidad());
        pedido.setMontoTotal(request.getMontoTotal());
        pedido.setTipo(TipoPedido.valueOf(request.getTipo().toUpperCase()));
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setDestino(request.getDestino());

        DescuentoStrategy strategy = strategyFactory.getStrategy(tipoCliente);
        pedido.setMontoTotal(strategy.calcular(pedido.getMontoTotal()));

        PedidoBase pedidoBase = pedidoFactory.crear(request.getTipo(), pedido);
        pedidoBase.aplicarReglas();

        Pedido guardado = pedidoRepository.save(pedido);

        eventManager.notify("PEDIDO_CREADO", guardado.getId(), "CREADO");

        return guardado;
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido obtenerPedido(Long id) {
        return pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + id));
    }

    public Pedido actualizarEstado(Long id, String estado) {
        Pedido pedido = obtenerPedido(id);
        pedido.setEstado(EstadoPedido.valueOf(estado.toUpperCase()));
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }
}