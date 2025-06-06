package com.alejandro.tarea7dwesalejandro.repositorios;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alejandro.tarea7dwesalejandro.modelo.Personas;

public interface PersonasRepository extends JpaRepository<Personas, Long> {

    Optional<Personas> findByEmail(String email);

    boolean existsByEmail(String email);
    
    @Query("SELECT p FROM Personas p JOIN p.credenciales c WHERE c.usuario = :usuario")
    Optional<Personas> findByUsuario(@Param("usuario") String usuario);

    List<Personas> findByRol(String rol);
    
    Optional<Personas> findByCredencialesUsuario(String usuario);
    

}