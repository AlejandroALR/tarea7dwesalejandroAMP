package com.alejandro.tarea7dwesalejandro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RegistroPersonaDTO {

    @NotBlank(message = "El nombre no puede estar vacío.")
    private String nombre;

    @NotBlank(message = "El correo no puede estar vacío.")
    @Email(message = "Formato de correo inválido.")
    @Pattern(regexp = "^\\S+$", message = "El correo no debe contener espacios.")
    private String email;

    @NotBlank(message = "El rol no puede estar vacío.")
    private String rol;

    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    @Pattern(regexp = "^\\S+$", message = "El nombre de usuario no debe contener espacios.")
    private String usuario;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Pattern(regexp = "^\\S+$", message = "La contraseña no debe contener espacios.")
    private String password;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
