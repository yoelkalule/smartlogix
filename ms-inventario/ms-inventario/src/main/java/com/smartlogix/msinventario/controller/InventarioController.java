package com.smartlogix.msinventario.controller;

import com.smartlogix.msinventario.dto.ProductoRequest;
import com.smartlogix.msinventario.model.Producto;
import com.smartlogix.msinventario.service.InventarioService;
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
    public ResponseEntity<Producto> crear(@RequestBody ProductoRequest request) {
        return ResponseEntity.ok(inventarioService.crearProducto(request));
    }

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(inventarioService.listarProductos());
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.obtenerProducto(id));
    }

    @PutMapping("/productos/{id}/stock")
    public ResponseEntity<Producto> actualizarStock(@PathVariable Long id,
                                                     @RequestParam Integer cantidad) {
        return ResponseEntity.ok(inventarioService.actualizarStock(id, cantidad));
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inventarioService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/productos/{id}/stock/devolver")
    public ResponseEntity<Producto> devolverStock(@PathVariable Long id,
                                               @RequestParam Integer cantidad) {
    return ResponseEntity.ok(inventarioService.devolverStock(id, cantidad));
}
}