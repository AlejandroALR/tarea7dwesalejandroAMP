package com.alejandro.tarea7dwesalejandro.repositorios;

import com.alejandro.tarea7dwesalejandro.modelo.ItemPedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
	
	List<ItemPedido> findByPedidoId(Long id);

}
