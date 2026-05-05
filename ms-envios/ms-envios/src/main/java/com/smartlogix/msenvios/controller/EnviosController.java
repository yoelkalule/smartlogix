package com.smartlogix.msenvios.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/envios")
public class EnviosController {

    @GetMapping("/test")
    public String test() {
        return "Microservicio de Envíos: ONLINE (Puerto 8083)";
    }
}