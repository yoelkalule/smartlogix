package com.smartlogix.msnotificaciones.controller;

import com.smartlogix.msnotificaciones.dto.NotificacionResponse;
import com.smartlogix.msnotificaciones.service.NotificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping("/test")
    public String test() {
        return "ms-notificaciones funcionando";
    }

    @PostMapping("/enviar")
    public ResponseEntity<NotificacionResponse> recibirNotificacion(@RequestParam Long pedidoId) {
        return ResponseEntity.ok(notificacionService.procesarEvento(pedidoId));
    }

    @PostMapping("/factory")
    public ResponseEntity<NotificacionResponse> enviarConFactory(@RequestParam Long pedidoId,
                                                                  @RequestParam String tipo,
                                                                  @RequestParam String mensaje) {
        return ResponseEntity.ok(notificacionService.enviarNotificacion(pedidoId, tipo, mensaje));
    }

    @GetMapping
    public ResponseEntity<List<NotificacionResponse>> listarTodas() {
        return ResponseEntity.ok(notificacionService.listarTodas());
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<NotificacionResponse>> listarPorPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(notificacionService.listarPorPedido(pedidoId));
    }
}