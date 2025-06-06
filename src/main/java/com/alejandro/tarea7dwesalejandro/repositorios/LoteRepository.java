package com.alejandro.tarea7dwesalejandro.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alejandro.tarea7dwesalejandro.modelo.Lote;
import com.alejandro.tarea7dwesalejandro.modelo.Personas;
import com.alejandro.tarea7dwesalejandro.modelo.Proveedor;

public interface LoteRepository extends JpaRepository<Lote, Long> {
	
    List<Lote> findByFechaHoraRecepcionIsNull();
    
    List<Lote> findByFechaHoraRecepcionIsNotNull();
    
    List<Lote> findByPersonaSolicitante(Personas persona);
    
    List<Lote> findByProveedorOrderByFechaHoraPeticionDesc(Proveedor proveedor);
    
    List<Lote> findByProveedor(Proveedor proveedor);
    
//    @Query("SELECT l FROM Lote l LEFT JOIN FETCH l.items WHERE l.proveedor = :proveedor")
//    List<Lote> findByProveedorConItems(@Param("proveedor") Proveedor proveedor);
    
    @Query("SELECT DISTINCT l FROM Lote l " +
    	       "LEFT JOIN FETCH l.items i " +
    	       "LEFT JOIN FETCH i.planta " +
    	       "WHERE l.proveedor = :proveedor")
    	List<Lote> findByProveedorConItems(@Param("proveedor") Proveedor proveedor);



}
