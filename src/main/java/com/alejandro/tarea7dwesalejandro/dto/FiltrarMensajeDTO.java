package com.alejandro.tarea7dwesalejandro.dto;

import java.time.LocalDate;

public class FiltrarMensajeDTO {

    private Long idPersona;
    private String codigoPlanta;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	public String getCodigoPlanta() {
		return codigoPlanta;
	}
	public void setCodigoPlanta(String codigoPlanta) {
		this.codigoPlanta = codigoPlanta;
	}
	public LocalDate getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(LocalDate fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public LocalDate getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(LocalDate fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

    
}
