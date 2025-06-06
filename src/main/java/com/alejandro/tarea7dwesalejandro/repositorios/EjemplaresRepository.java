package com.alejandro.tarea7dwesalejandro.repositorios;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import com.alejandro.tarea7dwesalejandro.modelo.Ejemplares;
import com.alejandro.tarea7dwesalejandro.modelo.Lote;
import com.alejandro.tarea7dwesalejandro.modelo.Plantas;

public interface EjemplaresRepository extends JpaRepository<Ejemplares, Long> {

    Optional<Ejemplares> findByNombre(String nombre);

    @Query("SELECT e FROM Ejemplares e WHERE e.planta.codigo = ?1")
    List<Ejemplares> findByTipo(String tipoCodigo);

    @Query("SELECT MAX(e.id) FROM Ejemplares e")
    Long findLastId();
    
    int countByPlanta_Codigo(String codigoPlanta);
    
    List<Ejemplares> findByPlanta_CodigoIn(List<String> codigos);
    
    @Query("SELECT e FROM Ejemplares e LEFT JOIN FETCH e.mensajes WHERE e.planta.codigo IN :codigos")
    List<Ejemplares> findWithMensajesByPlantaCodigoIn(@Param("codigos") List<String> codigos);

    @Query("SELECT e FROM Ejemplares e LEFT JOIN FETCH e.mensajes m LEFT JOIN FETCH m.persona WHERE e.id = :id")
    Optional<Ejemplares> findByIdConMensajesYPersonas(@Param("id") Long id);

    long countByPlanta(Plantas planta);
    
    @Query("SELECT e FROM Ejemplares e WHERE e.planta.codigo = :codigo AND e.disponible = true")
    List<Ejemplares> findByPlantaCodigoAndDisponibleTrue(@Param("codigo") String codigo, Pageable pageable);

    default List<Ejemplares> findFirstNDisponiblesPorPlanta(String codigo, int cantidad) {
        return findByPlantaCodigoAndDisponibleTrue(codigo, PageRequest.of(0, cantidad));
    }
    
    @Query("SELECT e.planta.codigo, e.planta.nombreComun, COUNT(e) " +
    	       "FROM Ejemplares e WHERE e.disponible = true " +
    	       "GROUP BY e.planta.codigo, e.planta.nombreComun")
    	List<Object[]> contarEjemplaresDisponiblesPorPlanta();

    	
    long countByPlanta_CodigoAndDisponibleTrue(String codPlanta);
    
    List<Ejemplares> findByLote(Lote lote);
    
    

}
