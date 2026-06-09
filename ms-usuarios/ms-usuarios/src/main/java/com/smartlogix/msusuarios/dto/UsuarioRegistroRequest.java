package com.smartlogix.msusuarios.dto;

import com.smartlogix.msusuarios.model.Rol;

public class UsuarioRegistroRequest {

    private String email;
    private String password;
    private String nombre;
    private Rol rol;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}