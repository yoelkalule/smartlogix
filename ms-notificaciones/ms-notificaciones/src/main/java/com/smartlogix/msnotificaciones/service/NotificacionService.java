package com.smartlogix.msnotificaciones.service;

import com.smartlogix.msnotificaciones.dto.NotificacionResponse;
import com.smartlogix.msnotificaciones.factory.NotificacionFactory;
import com.smartlogix.msnotificaciones.model.Notificacion;
import com.smartlogix.msnotificaciones.model.NotificacionRegistro;
import com.smartlogix.msnotificaciones.model.TipoNotificacion;
import com.smartlogix.msnotificaciones.repository.NotificacionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionService {

    private final NotificacionFactory notificacionFactory;
    private final NotificacionRepository notificacionRepository;

    public NotificacionService(NotificacionFactory notificacionFactory,
                               NotificacionRepository notificacionRepository) {
        this.notificacionFactory = notificacionFactory;
        this.notificacionRepository = notificacionRepository;
    }

    public NotificacionResponse procesarEvento(Long pedidoId) {
        String mensaje = "Se creo el pedido ID " + pedidoId;

        Notificacion notificacion = notificacionFactory.crear("EMAIL", pedidoId, mensaje);
        notificacion.enviar();

        NotificacionRegistro registro = guardarRegistro(pedidoId, TipoNotificacion.EMAIL, mensaje);

        return convertirAResponse(registro);
    }

    public NotificacionResponse enviarNotificacion(Long pedidoId, String tipo, String mensaje) {
        Notificacion notificacion = notificacionFactory.crear(tipo, pedidoId, mensaje);
        notificacion.enviar();

        NotificacionRegistro registro = guardarRegistro(
                pedidoId,
                TipoNotificacion.valueOf(tipo.toUpperCase()),
                mensaje
        );

        return convertirAResponse(registro);
    }

    private NotificacionRegistro guardarRegistro(Long pedidoId, TipoNotificacion tipo, String mensaje) {
        NotificacionRegistro registro = new NotificacionRegistro();
        registro.setPedidoId(pedidoId);
        registro.setTipo(tipo);
        registro.setMensaje(mensaje);
        registro.setFechaEnvio(LocalDateTime.now());
        registro.setEnviado(true);

        return notificacionRepository.save(registro);
    }

    public List<NotificacionResponse> listarTodas() {
        return notificacionRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public List<NotificacionResponse> listarPorPedido(Long pedidoId) {
        return notificacionRepository.findByPedidoId(pedidoId)
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    private NotificacionResponse convertirAResponse(NotificacionRegistro registro) {
        return new NotificacionResponse(
                registro.getId(),
                registro.getPedidoId(),
                registro.getTipo().name(),
                registro.getMensaje(),
                registro.getFechaEnvio(),
                registro.getEnviado()
        );
    }
}