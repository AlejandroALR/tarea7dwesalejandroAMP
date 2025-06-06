package com.alejandro.tarea7dwesalejandro.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alejandro.tarea7dwesalejandro.modelo.Proveedor;
import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    
	Optional<Proveedor> findByCif(String cif);
	
    boolean existsByCif(String cif);
    
    Optional<Proveedor> findByNombre(String nombre);
    
    Optional<Proveedor> findByCredenciales_Usuario(String usuario);

    
}
