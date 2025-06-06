package com.alejandro.tarea7dwesalejandro.dto;

import jakarta.validation.constraints.NotBlank;

public class RegistroMensajeDTO {

    private Long idEjemplar;

    @NotBlank(message = "El mensaje no puede estar vacío.")
    private String mensaje;

	public Long getIdEjemplar() {
		return idEjemplar;
	}

	public void setIdEjemplar(Long idEjemplar) {
		this.idEjemplar = idEjemplar;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

    
}

