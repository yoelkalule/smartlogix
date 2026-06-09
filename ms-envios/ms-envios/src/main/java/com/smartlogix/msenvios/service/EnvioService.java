package com.smartlogix.msenvios.service;

import com.smartlogix.msenvios.dto.EnvioRequest;
import com.smartlogix.msenvios.dto.EnvioResponse;
import com.smartlogix.msenvios.model.Envio;
import com.smartlogix.msenvios.model.EstadoEnvio;
import com.smartlogix.msenvios.repository.EnvioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EnvioService {

    private final EnvioRepository envioRepository;

    public EnvioService(EnvioRepository envioRepository) {
        this.envioRepository = envioRepository;
    }

    public EnvioResponse crearEnvio(EnvioRequest request) {
        Envio envio = new Envio();
        envio.setPedidoId(request.getPedidoId());
        envio.setDestino(request.getDestino());
        envio.setTransportista(request.getTransportista());
        envio.setNumeroTracking("TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        envio.setEstado(EstadoEnvio.ASIGNADO);

        Envio guardado = envioRepository.save(envio);

        return convertirAResponse(guardado);
    }

    public List<EnvioResponse> listarEnvios() {
        return envioRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public EnvioResponse obtenerEnvio(Long id) {
        return convertirAResponse(buscarEnvioEntidad(id));
    }

    public EnvioResponse obtenerPorPedido(Long pedidoId) {
        Envio envio = envioRepository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new RuntimeException("Envio no encontrado para pedido: " + pedidoId));

        return convertirAResponse(envio);
    }

    public EnvioResponse actualizarEstado(Long id, String estado) {
        Envio envio = buscarEnvioEntidad(id);
        envio.setEstado(EstadoEnvio.valueOf(estado.toUpperCase()));

        Envio actualizado = envioRepository.save(envio);

        return convertirAResponse(actualizado);
    }

    private Envio buscarEnvioEntidad(Long id) {
        return envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envio no encontrado: " + id));
    }

    private EnvioResponse convertirAResponse(Envio envio) {
        return new EnvioResponse(
                envio.getId(),
                envio.getPedidoId(),
                envio.getDestino(),
                envio.getTransportista(),
                envio.getNumeroTracking(),
                envio.getEstado().name()
        );
    }
}