package com.smartlogix.mspedidos.controller;

import com.smartlogix.mspedidos.dto.PedidoRequest;
import com.smartlogix.mspedidos.dto.PedidoResponse;
import com.smartlogix.mspedidos.service.PedidoService;
import com.smartlogix.mspedidos.strategy.DescuentoStrategy;
import com.smartlogix.mspedidos.strategy.DescuentoStrategyFactory;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidosController {

    private final PedidoService pedidoService;
    private final DescuentoStrategyFactory strategyFactory;

    public PedidosController(PedidoService pedidoService,
                              DescuentoStrategyFactory strategyFactory) {
        this.pedidoService = pedidoService;
        this.strategyFactory = strategyFactory;
    }

    @GetMapping("/test")
    public String test() {
        return "ms-pedidos funcionando";
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody PedidoRequest request,
                                                 @RequestParam(defaultValue = "NORMAL") String tipoCliente) {
        return ResponseEntity.ok(pedidoService.crearPedido(request, tipoCliente));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listar() {
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.obtenerPedido(id));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<PedidoResponse> actualizarEstado(@PathVariable Long id,
                                                            @RequestParam String estado) {
        return ResponseEntity.ok(pedidoService.actualizarEstado(id, estado));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PedidoResponse>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(pedidoService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/calcular")
    public ResponseEntity<String> calcularDescuento(@RequestParam String monto,
                                                     @RequestParam String tipoCliente) {
        double montoDouble = Double.parseDouble(monto);
        DescuentoStrategy strategy = strategyFactory.getStrategy(tipoCliente);
        double montoFinal = strategy.calcular(montoDouble);

        return ResponseEntity.ok(String.format(
            "Strategy: %s | Monto original: $%.2f | Monto final: $%.2f",
            strategy.getNombre(), montoDouble, montoFinal
        ));
    }
}