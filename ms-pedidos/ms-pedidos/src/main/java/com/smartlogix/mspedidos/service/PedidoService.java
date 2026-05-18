package com.smartlogix.mspedidos.service;

import com.smartlogix.mspedidos.dto.PedidoRequest;
import com.smartlogix.mspedidos.facade.LogisticaFacade;
import com.smartlogix.mspedidos.model.EstadoPedido;
import com.smartlogix.mspedidos.model.Pedido;
import com.smartlogix.mspedidos.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final LogisticaFacade logisticaFacade;
    private final PedidoRepository pedidoRepository;

    public PedidoService(LogisticaFacade logisticaFacade,
                         PedidoRepository pedidoRepository) {
        this.logisticaFacade = logisticaFacade;
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido crearPedido(PedidoRequest request, String tipoCliente) {
        return logisticaFacade.procesarPedido(request, tipoCliente);
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