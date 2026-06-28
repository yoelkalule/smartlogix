package com.smartlogix.msinventario.controller;

import com.smartlogix.msinventario.dto.ProductoRequest;
import com.smartlogix.msinventario.dto.ProductoResponse;
import com.smartlogix.msinventario.service.InventarioService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping("/test")
    public String test() {
        return "ms-inventario funcionando";
    }

    @PostMapping("/productos")
    public ResponseEntity<?> crear(@Valid @RequestBody ProductoRequest request){
        return ResponseEntity.ok(inventarioService.crearProducto(request));
    }

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoResponse>> listar() {
        return ResponseEntity.ok(inventarioService.listarProductos());
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.obtenerProducto(id));
    }

    @PutMapping("/productos/{id}/stock")
    public ResponseEntity<ProductoResponse> actualizarStock(@PathVariable Long id,
                                                             @RequestParam Integer cantidad) {
        return ResponseEntity.ok(inventarioService.actualizarStock(id, cantidad));
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inventarioService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/productos/{id}/stock/devolver")
    public ResponseEntity<ProductoResponse> devolverStock(@PathVariable Long id,
                                                           @RequestParam Integer cantidad) {
        return ResponseEntity.ok(inventarioService.devolverStock(id, cantidad));
    }
}