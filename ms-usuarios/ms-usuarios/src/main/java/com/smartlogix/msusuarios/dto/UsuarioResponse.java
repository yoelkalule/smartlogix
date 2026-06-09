package com.smartlogix.msusuarios.dto;

public class UsuarioResponse {

    private Long id;
    private String email;
    private String nombre;
    private String rol;

    public UsuarioResponse(Long id, String email, String nombre, String rol) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRol() {
        return rol;
    }
}