package com.alejandro.tarea7dwesalejandro.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alejandro.tarea7dwesalejandro.modelo.ComposicionLote;
import com.alejandro.tarea7dwesalejandro.modelo.Lote;
import com.alejandro.tarea7dwesalejandro.repositorios.ComposicionLoteRepository;

@Service
public class ServiciosComposicionLote {

    @Autowired
    private ComposicionLoteRepository composicionLoteRepository;

    public void guardarComposicion(ComposicionLote composicion) {
        composicionLoteRepository.save(composicion);
    }

    public List<ComposicionLote> obtenerPorLote(Lote lote) {
        return composicionLoteRepository.findByLote(lote);
    }

    public void guardarTodas(List<ComposicionLote> lista) {
        composicionLoteRepository.saveAll(lista);
    }
}
