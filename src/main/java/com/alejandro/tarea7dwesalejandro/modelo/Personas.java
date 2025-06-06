package com.alejandro.tarea7dwesalejandro.modelo;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Table(name = "Personas")
@Inheritance(strategy = InheritanceType.JOINED)
public class Personas implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String nombre;
	
	@Column(unique=true)
	private String email;
	
	@Column(nullable = false)
	private String rol;
	
	@OneToOne
	(mappedBy = "persona", cascade = CascadeType.ALL)
	private Credenciales credenciales;
	
	public Personas() {}
	
	public Personas(Long id, String nombre, String email, String rol) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.rol = rol;
	}
	
	public Personas(String nombre, String email) {
		super();
		this.nombre = nombre;
		this.email = email;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRol() {
	    return rol;
	}

	public void setRol(String rol) {
	    this.rol = rol;
	}


	public Credenciales getCredenciales() {
		return credenciales;
	}

	public void setCredenciales(Credenciales credenciales) {
		this.credenciales = credenciales;
	}

	@Override
	public int hashCode() {
		return Objects.hash(credenciales, email, id, nombre, rol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Personas other = (Personas) obj;
		return Objects.equals(credenciales, other.credenciales) && Objects.equals(email, other.email)
				&& Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre) && Objects.equals(rol, other.rol);
	}

	@Override
	public String toString() {
		return "Personas [id=" + id + ", nombre=" + nombre + ", email=" + email + ", rol=" + rol + ", credenciales=" + credenciales + "]";
	}

}