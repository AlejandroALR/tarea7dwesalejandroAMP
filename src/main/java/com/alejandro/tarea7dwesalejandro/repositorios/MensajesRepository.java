package com.alejandro.tarea7dwesalejandro.repositorios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alejandro.tarea7dwesalejandro.modelo.Ejemplares;
import com.alejandro.tarea7dwesalejandro.modelo.Mensajes;

public interface MensajesRepository extends JpaRepository<Mensajes, Long> {

    List<Mensajes> findByEjemplarId(Long idEjemplar);

    List<Mensajes> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT COUNT(m) FROM Mensajes m WHERE m.ejemplar.id = ?1")
    int contarMensajesPorEjemplar(Long idEjemplar);

    @Query("SELECT m.fecha FROM Mensajes m WHERE m.ejemplar.id = ?1 ORDER BY m.fecha DESC LIMIT 1")
    Optional<LocalDateTime> obtenerUltimaFechaMensaje(Long idEjemplar);

    @Query("SELECT m FROM Mensajes m WHERE LOWER(m.persona.nombre) = LOWER(?1)")
    List<Mensajes> findByNombrePersona(String nombre);

    @Query("""
        SELECT m FROM Mensajes m
        JOIN m.ejemplar e
        JOIN e.planta p
        WHERE LOWER(p.codigo) = LOWER(?1) OR LOWER(p.nombreComun) = LOWER(?1)
        """)
    List<Mensajes> findByCodigoPlanta(String codigoPlanta);
    
    List<Mensajes> findByEjemplarOrderByFechaDesc(Ejemplares ejemplar);

    List<Mensajes> findByEjemplarPlantaCodigo(String codigoPlanta);
    
    List<Mensajes> findByPersonaId(Long id);

    Optional<Mensajes> findFirstByEjemplar_Lote_Id(Long idLote);
}
