package com.alejandro.tarea7dwesalejandro.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "plantas")
public class Plantas implements Serializable {

    @Id
    @Column(length = 10)
    @NotBlank(message = "El código no puede estar vacío.")
    @Pattern(regexp = "^\\S+$", message = "El código no debe contener espacios.")
    private String codigo;

    @Column(name = "nombreComun")
    private String nombreComun;

    @Column(name = "nombreCientifico")
    private String nombreCientifico;

    @OneToMany(mappedBy = "planta", cascade = CascadeType.ALL)
    private Set<Ejemplares> ejemplares = new HashSet<>();

    public Plantas() {}

    public Plantas(String codigo, String nombreComun, String nombreCientifico) {
        this.codigo = codigo;
        this.nombreComun = nombreComun;
        this.nombreCientifico = nombreCientifico;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

    public Set<Ejemplares> getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(Set<Ejemplares> ejemplares) {
        this.ejemplares = ejemplares;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Plantas)) return false;
        Plantas other = (Plantas) obj;
        return Objects.equals(codigo, other.codigo);
    }

    @Override
    public String toString() {
        return "Plantas [codigo=" + codigo + ", nombreComun=" + nombreComun + ", nombreCientifico=" + nombreCientifico + "]";
    }
}

