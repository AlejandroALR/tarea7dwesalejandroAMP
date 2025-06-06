package com.alejandro.tarea7dwesalejandro.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@Table(name = "proveedores", uniqueConstraints = @UniqueConstraint(columnNames = "cif"))
public class Proveedor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El CIF no puede estar vacío")
    @Pattern(regexp = "^[A-Z][0-9]{8}$", message = "El CIF debe tener el formato correcto (una letra seguida de 8 números)")
    private String cif;
    
    @Column(nullable = false)
    private String rol = "PROVEEDOR";
    
    
    @OneToOne(mappedBy = "proveedor")
    private Credenciales credenciales;
    
//    @Column
//    private String usuario = credenciales.getUsuario();
    
    @Column(nullable = false)
    private String usuario;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
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
    
    
}
