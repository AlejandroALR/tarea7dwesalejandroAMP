package com.alejandro.tarea7dwesalejandro.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alejandro.tarea7dwesalejandro.modelo.Credenciales;
import com.alejandro.tarea7dwesalejandro.modelo.Personas;

public interface CredencialesRepository extends JpaRepository<Credenciales, Long> {

    boolean existsByUsuario(String usuario);   
    Optional<Credenciales> findByUsuario(String usuario);
    Optional<Credenciales> findByPersona(Personas persona);
    
//    Optional<Credenciales> findByNombreUsuario(String usuario);


    
}

