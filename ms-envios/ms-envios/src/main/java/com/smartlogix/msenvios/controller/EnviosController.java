package com.smartlogix.msenvios.controller;

import com.smartlogix.msenvios.dto.EnvioRequest;
import com.smartlogix.msenvios.dto.EnvioResponse;
import com.smartlogix.msenvios.service.EnvioService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/envios")
public class EnviosController {

    private final EnvioService envioService;

    public EnviosController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @GetMapping("/test")
    public String test() {
        return "ms-envios funcionando";
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody EnvioRequest request){
        return ResponseEntity.ok(envioService.crearEnvio(request));
    }

    @GetMapping
    public ResponseEntity<List<EnvioResponse>> listar() {
        return ResponseEntity.ok(envioService.listarEnvios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvioResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(envioService.obtenerEnvio(id));
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<EnvioResponse> obtenerPorPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(envioService.obtenerPorPedido(pedidoId));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<EnvioResponse> actualizarEstado(@PathVariable Long id,
                                                           @RequestParam String estado) {
        return ResponseEntity.ok(envioService.actualizarEstado(id, estado));
    }
}