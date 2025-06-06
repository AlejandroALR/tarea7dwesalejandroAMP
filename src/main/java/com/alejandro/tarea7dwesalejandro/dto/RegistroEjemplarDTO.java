package com.alejandro.tarea7dwesalejandro.dto;

import jakarta.validation.constraints.NotBlank;

public class RegistroEjemplarDTO {

    @NotBlank(message = "Debe seleccionar una planta.")
    private String codigoPlanta;

    public String getCodigoPlanta() {
        return codigoPlanta;
    }

    public void setCodigoPlanta(String codigoPlanta) {
        this.codigoPlanta = codigoPlanta;
    }

}

