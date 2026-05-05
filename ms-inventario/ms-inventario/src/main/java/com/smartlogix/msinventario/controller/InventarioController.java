package com.smartlogix.msinventario.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @GetMapping("/test")
    public String test() {
        return "Microservicio de Inventario: ONLINE (Puerto 8081)";
    }
}