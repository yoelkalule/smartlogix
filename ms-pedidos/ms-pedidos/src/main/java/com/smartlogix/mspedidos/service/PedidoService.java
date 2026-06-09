package com.smartlogix.mspedidos.service;

import com.smartlogix.mspedidos.dto.PedidoRequest;
import com.smartlogix.mspedidos.dto.PedidoResponse;
import com.smartlogix.mspedidos.facade.LogisticaFacade;
import com.smartlogix.mspedidos.model.EstadoPedido;
import com.smartlogix.mspedidos.model.Pedido;
import com.smartlogix.mspedidos.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final LogisticaFacade logisticaFacade;
    private final PedidoRepository pedidoRepository;

    public PedidoService(LogisticaFacade logisticaFacade,
                         PedidoRepository pedidoRepository) {
        this.logisticaFacade = logisticaFacade;
        this.pedidoRepository = pedidoRepository;
    }

    public PedidoResponse crearPedido(PedidoRequest request, String tipoCliente) {
        Pedido pedido = logisticaFacade.procesarPedido(request, tipoCliente);
        return convertirAResponse(pedido);
    }

    public List<PedidoResponse> listarPedidos() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public PedidoResponse obtenerPedido(Long id) {
        Pedido pedido = buscarPedidoEntidad(id);
        return convertirAResponse(pedido);
    }

    public PedidoResponse actualizarEstado(Long id, String estado) {
        Pedido pedido = buscarPedidoEntidad(id);
        pedido.setEstado(EstadoPedido.valueOf(estado.toUpperCase()));
        Pedido actualizado = pedidoRepository.save(pedido);
        return convertirAResponse(actualizado);
    }

    public List<PedidoResponse> listarPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    private Pedido buscarPedidoEntidad(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + id));
    }

    private PedidoResponse convertirAResponse(Pedido pedido) {
        return new PedidoResponse(
                pedido.getId(),
                pedido.getUsuarioId(),
                pedido.getProductoId(),
                pedido.getCantidad(),
                pedido.getMontoTotal(),
                pedido.getEstado().name(),
                pedido.getDestino(),
                pedido.getObservaciones()
        );
    }
}