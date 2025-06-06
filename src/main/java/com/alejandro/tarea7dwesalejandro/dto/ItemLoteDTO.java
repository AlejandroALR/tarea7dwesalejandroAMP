package com.alejandro.tarea7dwesalejandro.dto;

public class ItemLoteDTO {
    private String codigoPlanta;
    private String nombrePlanta;
    private int cantidad;

    public ItemLoteDTO(String codigoPlanta, String nombrePlanta, int cantidad) {
        this.codigoPlanta = codigoPlanta;
        this.nombrePlanta = nombrePlanta;
        this.cantidad = cantidad;
    }

    public String getCodigoPlanta() {
        return codigoPlanta;
    }

    public void setCodigoPlanta(String codigoPlanta) {
        this.codigoPlanta = codigoPlanta;
    }

    public String getNombrePlanta() {
        return nombrePlanta;
    }

    public void setNombrePlanta(String nombrePlanta) {
        this.nombrePlanta = nombrePlanta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
