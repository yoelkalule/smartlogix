package com.smartlogix.msusuarios.dto;

import com.smartlogix.msusuarios.model.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioRegistroRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @Email(message = "El email debe tener formato valido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "La password es obligatoria")
    @Size(min = 4, message = "La password debe tener al menos 4 caracteres")
    private String password;

    @NotNull(message = "El rol es obligatorio")
    private Rol rol;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}