package com.alejandro.tarea7dwesalejandro.repositorios;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alejandro.tarea7dwesalejandro.modelo.Plantas;

public interface PlantasRepository extends JpaRepository<Plantas, String> {
	Optional<Plantas> findByCodigo(String codigo);

	void deleteByCodigo(String codigo);

	boolean existsByCodigo(String codigo);
	
	@Query("SELECT DISTINCT p FROM Plantas p LEFT JOIN FETCH p.ejemplares")
	List<Plantas> findAllConEjemplares();
	
	List<Plantas> findByNombreComunContainingIgnoreCase(String texto);
	
	

}
