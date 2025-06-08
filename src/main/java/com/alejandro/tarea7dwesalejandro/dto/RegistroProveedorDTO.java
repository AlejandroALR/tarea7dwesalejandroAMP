package com.alejandro.tarea7dwesalejandro.dto;

import jakarta.validation.constraints.*;

public class RegistroProveedorDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El CIF no puede estar vacío")
    @Pattern(regexp = "^[A-Z][0-9]{8}$", message = "El CIF debe tener el formato correcto (una letra seguida de 8 números)")
    private String cif;

    @NotBlank
    @Pattern(regexp = "^[^\\s]+$", message = "El nombre de usuario no puede contener espacios")
    private String usuario;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Pattern(regexp = "\\S+", message = "No puede contener espacios")
    private String password;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
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

