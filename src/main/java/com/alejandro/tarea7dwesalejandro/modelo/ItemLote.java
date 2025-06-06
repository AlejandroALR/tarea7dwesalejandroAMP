package com.alejandro.tarea7dwesalejandro.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "items_lote")
public class ItemLote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_lote")
    private Lote lote;

    @ManyToOne
    @JoinColumn(name = "cod_planta")
    private Plantas planta;


    private int cantidad;


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
