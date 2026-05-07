package com.smartlogix.msenvios.service;

import com.smartlogix.msenvios.dto.EnvioRequest;
import com.smartlogix.msenvios.model.Envio;
import com.smartlogix.msenvios.model.EstadoEnvio;
import com.smartlogix.msenvios.repository.EnvioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EnvioService {

    private final EnvioRepository envioRepository;

    public EnvioService(EnvioRepository envioRepository) {
        this.envioRepository = envioRepository;
    }

    public Envio crearEnvio(EnvioRequest request) {
        Envio envio = new Envio();
        envio.setPedidoId(request.getPedidoId());
        envio.setDestino(request.getDestino());
        envio.setTransportista(request.getTransportista());
        envio.setNumeroTracking("TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        envio.setEstado(EstadoEnvio.ASIGNADO);
        return envioRepository.save(envio);
    }

    public List<Envio> listarEnvios() {
        return envioRepository.findAll();
    }

    public Envio obtenerEnvio(Long id) {
        return envioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Envio no encontrado: " + id));
    }

    public Envio obtenerPorPedido(Long pedidoId) {
        return envioRepository.findByPedidoId(pedidoId)
            .orElseThrow(() -> new RuntimeException("Envio no encontrado para pedido: " + pedidoId));
    }

    public Envio actualizarEstado(Long id, String estado) {
        Envio envio = obtenerEnvio(id);
        envio.setEstado(EstadoEnvio.valueOf(estado.toUpperCase()));
        return envioRepository.save(envio);
    }
}