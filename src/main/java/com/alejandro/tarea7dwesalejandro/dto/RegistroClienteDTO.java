package com.alejandro.tarea7dwesalejandro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class RegistroClienteDTO {

    @NotBlank(message = "El nombre completo no puede estar vacío.")
    @Size(max = 100, message = "El nombre completo no puede superar los 100 caracteres.")
    private String nombreCompleto;

    @NotNull(message = "La fecha de nacimiento es obligatoria.")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El NIF/NIE no puede estar vacío.")
    @Pattern(regexp = "^[0-9XYZ][0-9]{7}[A-Z]$", message = "El NIF/NIE tiene un formato inválido.")
    @Pattern(regexp = "^\\S+$", message = "El NIF/NIE no debe contener espacios.")
    private String nifNie;

    @NotBlank(message = "La dirección no puede estar vacía.")
    @Size(max = 150, message = "La dirección no puede superar los 150 caracteres.")
    private String direccion;

    @NotBlank(message = "El teléfono no puede estar vacío.")
    @Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe tener 9 dígitos.")
    @Pattern(regexp = "^\\S+$", message = "El teléfono no debe contener espacios.")
    private String telefono;

    @NotBlank(message = "El email no puede estar vacío.")
    @Email(message = "El formato del email no es válido.")
    @Pattern(regexp = "^\\S+$", message = "El email no debe contener espacios.")
    private String email;

    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    @Size(min = 4, max = 30, message = "El usuario debe tener entre 4 y 30 caracteres.")
    @Pattern(regexp = "^\\S+$", message = "El usuario no debe contener espacios.")
    private String usuario;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres.")
    @Pattern(regexp = "^\\S+$", message = "La contraseña no debe contener espacios.")
    private String password;


    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNifNie() {
        return nifNie;
    }

    public void setNifNie(String nifNie) {
        this.nifNie = nifNie;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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


