package com.alejandro.tarea7dwesalejandro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RegistroPlantaDTO {

    @NotBlank(message = "El código no puede estar vacío.")
    @Pattern(regexp = "^\\S+$", message = "El código no debe contener espacios.")
    private String codigo;

    @NotBlank(message = "El nombre científico no puede estar vacío.")
    private String nombreCientifico;

    @NotBlank(message = "El nombre común no puede estar vacío.")
    private String nombreComun;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombreCientifico() {
		return nombreCientifico;
	}

	public void setNombreCientifico(String nombreCientifico) {
		this.nombreCientifico = nombreCientifico;
	}

	public String getNombreComun() {
		return nombreComun;
	}

	public void setNombreComun(String nombreComun) {
		this.nombreComun = nombreComun;
	}

    
}
