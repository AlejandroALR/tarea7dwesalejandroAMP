package com.alejandro.tarea7dwesalejandro.repositorios;

import com.alejandro.tarea7dwesalejandro.modelo.ItemLote;
import com.alejandro.tarea7dwesalejandro.modelo.Lote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemLoteRepository extends JpaRepository<ItemLote, Long> {
    List<ItemLote> findByLote(Lote lote);
}
