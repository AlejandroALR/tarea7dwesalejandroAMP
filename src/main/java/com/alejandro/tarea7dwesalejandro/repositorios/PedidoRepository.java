package com.alejandro.tarea7dwesalejandro.repositorios;

import com.alejandro.tarea7dwesalejandro.modelo.Pedido;
import com.alejandro.tarea7dwesalejandro.modelo.Personas;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	
//	@EntityGraph(attributePaths = {"items"})
//	List<Pedido> findByPersona(Personas persona);
	
	List<Pedido> findByFechaBetween(LocalDate desde, LocalDate hasta);
	
	@EntityGraph(attributePaths = {"items"})
	List<Pedido> findWithItemsByPersona(Personas persona);

}
