package com.alejandro.tarea7dwesalejandro.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alejandro.tarea7dwesalejandro.modelo.ComposicionLote;
import com.alejandro.tarea7dwesalejandro.modelo.Lote;

public interface ComposicionLoteRepository extends JpaRepository<ComposicionLote, Long> {
    List<ComposicionLote> findByLote(Lote lote);
    
    
}
