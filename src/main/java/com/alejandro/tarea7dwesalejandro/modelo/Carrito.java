package com.alejandro.tarea7dwesalejandro.modelo;

import java.util.ArrayList;
import java.util.List;

public class Carrito {

    private List<ItemCarrito> items = new ArrayList<>();

    public void agregarItem(ItemCarrito item) {
        this.items.add(item);
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    public void vaciar() {
        this.items.clear();
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }
    
    public void eliminarItem(String codigoPlanta) {
        items.removeIf(item -> item.getCodigo().equals(codigoPlanta));
    }

}
