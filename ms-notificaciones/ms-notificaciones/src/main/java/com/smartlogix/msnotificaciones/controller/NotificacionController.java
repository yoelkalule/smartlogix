package com.smartlogix.msnotificaciones.controller;

import com.smartlogix.msnotificaciones.factory.NotificacionFactory;
import com.smartlogix.msnotificaciones.model.Notificacion;
import com.smartlogix.msnotificaciones.service.NotificacionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;
    private final NotificacionFactory notificacionFactory;

    public NotificacionController(NotificacionService notificacionService,
                                  NotificacionFactory notificacionFactory) {
        this.notificacionService = notificacionService;
        this.notificacionFactory = notificacionFactory;
    }

    @PostMapping("/enviar")
    public String recibirNotificacion(@RequestParam Long pedidoId) {
        notificacionService.procesarEvento(pedidoId);
        return "Notificación procesada correctamente";
    }

    @PostMapping("/factory")
    public String enviarConFactory(@RequestParam Long pedidoId,
                                   @RequestParam String tipo,
                                   @RequestParam String mensaje) {
        Notificacion notificacion = notificacionFactory.crear(tipo, pedidoId, mensaje);
        notificacion.enviar();
        return "Notificacion tipo " + tipo + " enviada para pedido #" + pedidoId;
    }
}