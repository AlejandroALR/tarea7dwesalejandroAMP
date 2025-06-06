package com.alejandro.tarea7dwesalejandro.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Clientes")
@PrimaryKeyJoinColumn(name = "persona_id")
public class Clientes extends Personas {

	private static final long serialVersionUID = 1L;

	private LocalDate fechaNacimiento;

	private String nifNie;

	private String direccionEnvio;

	private String telefono;

	public Clientes() {
		super();
		this.setRol("CLIENTE");
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

	public String getDireccionEnvio() {
		return direccionEnvio;
	}

	public void setDireccionEnvio(String direccionEnvio) {
		this.direccionEnvio = direccionEnvio;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
}
