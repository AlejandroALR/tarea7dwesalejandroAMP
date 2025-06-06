package com.alejandro.tarea7dwesalejandro.dto;

import java.time.LocalDateTime;

public class EjemplarResumenDTO {
    private String codigoEjemplar;
    private String nombrePlanta;
    private int numeroMensajes;
    private LocalDateTime fechaUltimoMensaje;

    public EjemplarResumenDTO(String codigoEjemplar, String nombrePlanta, int numeroMensajes, LocalDateTime fechaUltimoMensaje) {
        this.codigoEjemplar = codigoEjemplar;
        this.nombrePlanta = nombrePlanta;
        this.numeroMensajes = numeroMensajes;
        this.fechaUltimoMensaje = fechaUltimoMensaje;
    }

    public String getCodigoEjemplar() {
        return codigoEjemplar;
    }

    public String getNombrePlanta() {
        return nombrePlanta;
    }

    public int getNumeroMensajes() {
        return numeroMensajes;
    }

    public LocalDateTime getFechaUltimoMensaje() {
        return fechaUltimoMensaje;
    }
}
