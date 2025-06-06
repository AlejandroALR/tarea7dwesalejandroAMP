package com.alejandro.tarea7dwesalejandro.modelo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "lotes")
public class Lote implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Proveedor proveedor;

    @ManyToOne
    private Personas personaSolicitante;
    
//    @ManyToOne
//    private Personas personaReceptora;

    private LocalDateTime fechaHoraPeticion;

    private LocalDateTime fechaHoraRecepcion;

    private boolean urgente;
    
    @Column(nullable = false)
    private String estado = "NUEVO";

    @OneToMany(mappedBy = "lote", cascade = CascadeType.ALL)
    private List<ItemLote> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Personas getPersonaSolicitante() {
        return personaSolicitante;
    }

    public void setPersonaSolicitante(Personas personaSolicitante) {
        this.personaSolicitante = personaSolicitante;
    }

    public LocalDateTime getFechaHoraPeticion() {
        return fechaHoraPeticion;
    }

    public void setFechaHoraPeticion(LocalDateTime fechaHoraPeticion) {
        this.fechaHoraPeticion = fechaHoraPeticion;
    }

    public LocalDateTime getFechaHoraRecepcion() {
        return fechaHoraRecepcion;
    }

    public void setFechaHoraRecepcion(LocalDateTime fechaHoraRecepcion) {
        this.fechaHoraRecepcion = fechaHoraRecepcion;
    }

    public boolean isUrgente() {
        return urgente;
    }

    public void setUrgente(boolean urgente) {
        this.urgente = urgente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public List<ItemLote> getItems() {
        return items;
    }

    public void setItems(List<ItemLote> items) {
        this.items = items;
    }
    
}
