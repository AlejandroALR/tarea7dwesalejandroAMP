package com.alejandro.tarea7dwesalejandro.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "composiciones_lote")
public class ComposicionLote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Lote lote;

    @ManyToOne(optional = false)
    private Plantas planta;

    @Column(nullable = false)
    private int cantidad;

    public ComposicionLote() {}

    public ComposicionLote(Lote lote, Plantas planta, int cantidad) {
        this.lote = lote;
        this.planta = planta;
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public Plantas getPlanta() {
        return planta;
    }

    public void setPlanta(Plantas planta) {
        this.planta = planta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
