package com.alejandro.tarea7dwesalejandro.modelo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "Ejemplares")
public class Ejemplares implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "fk_planta", referencedColumnName = "codigo", nullable = false)
    private Plantas planta;

    @OneToMany(mappedBy = "ejemplar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mensajes> mensajes = new LinkedList<>();

    @Column(name = "disponible", nullable = false)
    private boolean disponible = true;
    
    @ManyToOne
    private Lote lote;

    public Ejemplares() {
    }

    public Ejemplares(String nombre, Plantas planta) {
        this.nombre = nombre;
        this.planta = planta;
        this.disponible = true;
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

    public Plantas getPlanta() {
        return planta;
    }

    public void setPlanta(Plantas planta) {
        this.planta = planta;
    }

    public List<Mensajes> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensajes> mensajes) {
        this.mensajes = mensajes;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }


    
}

