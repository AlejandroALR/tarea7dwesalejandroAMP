package com.alejandro.tarea7dwesalejandro.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Mensajes")
public class Mensajes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private LocalDateTime fecha;
	
	@Column(nullable=false)
	private String mensaje;
	
	@ManyToOne
	@JoinColumn(name="fk_idPersona", nullable=false)
	private Personas persona;
	
	@ManyToOne
	@JoinColumn(name="fk_idEjemplar", nullable=false)
	private Ejemplares ejemplar;

	public Mensajes() {}
	
	public Mensajes(LocalDateTime fecha, String mensaje, Personas persona, Ejemplares ejemplar) {
		super();
		this.fecha = fecha;
		this.mensaje = mensaje;
		this.persona = persona;
		this.ejemplar = ejemplar;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Personas getPersona() {
		return persona;
	}

	public void setPersona(Personas persona) {
		this.persona = persona;
	}

	public Ejemplares getEjemplar() {
		return ejemplar;
	}

	public void setEjemplar(Ejemplares ejemplar) {
		this.ejemplar = ejemplar;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ejemplar, fecha, id, mensaje, persona);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mensajes other = (Mensajes) obj;
		return Objects.equals(ejemplar, other.ejemplar) && Objects.equals(fecha, other.fecha)
				&& Objects.equals(id, other.id) && Objects.equals(mensaje, other.mensaje)
				&& Objects.equals(persona, other.persona);
	}

	@Override
	public String toString() {
		return "Mensajes [id=" + id + ", fecha=" + fecha + ", mensaje=" + mensaje + ", persona=" + persona
				+ ", ejemplar=" + ejemplar + "]";
	}
	
	
}
