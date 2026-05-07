package com.smartlogix.msnotificaciones.service;

import com.smartlogix.msnotificaciones.factory.NotificacionFactory;
import com.smartlogix.msnotificaciones.model.Notificacion;
import com.smartlogix.msnotificaciones.model.NotificacionRegistro;
import com.smartlogix.msnotificaciones.model.TipoNotificacion;
import com.smartlogix.msnotificaciones.repository.NotificacionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacionService {

    private final NotificacionFactory notificacionFactory;
    private final NotificacionRepository notificacionRepository;

    public NotificacionService(NotificacionFactory notificacionFactory,
                               NotificacionRepository notificacionRepository) {
        this.notificacionFactory = notificacionFactory;
        this.notificacionRepository = notificacionRepository;
    }

    public NotificacionRegistro procesarEvento(Long pedidoId) {
        String mensaje = "Se creo el pedido ID " + pedidoId;

        Notificacion notificacion = notificacionFactory.crear("EMAIL", pedidoId, mensaje);
        notificacion.enviar();

        return guardarRegistro(pedidoId, TipoNotificacion.EMAIL, mensaje);
    }

    public NotificacionRegistro enviarNotificacion(Long pedidoId, String tipo, String mensaje) {
        Notificacion notificacion = notificacionFactory.crear(tipo, pedidoId, mensaje);
        notificacion.enviar();

        return guardarRegistro(pedidoId, TipoNotificacion.valueOf(tipo.toUpperCase()), mensaje);
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

    public List<NotificacionRegistro> listarTodas() {
        return notificacionRepository.findAll();
    }

    public List<NotificacionRegistro> listarPorPedido(Long pedidoId) {
        return notificacionRepository.findByPedidoId(pedidoId);
    }
}