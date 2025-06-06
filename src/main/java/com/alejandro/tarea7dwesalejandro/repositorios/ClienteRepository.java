package com.alejandro.tarea7dwesalejandro.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alejandro.tarea7dwesalejandro.modelo.Clientes;

public interface ClienteRepository extends JpaRepository<Clientes, Long> {
	
	boolean existsByEmail(String email);
	
	boolean existsByCredencialesUsuario(String usuario);

	boolean existsByNifNie(String nifNie);

	Optional<Clientes> findByEmail(String email);
}
