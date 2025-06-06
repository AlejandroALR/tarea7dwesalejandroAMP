package com.alejandro.tarea7dwesalejandro.servicios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alejandro.tarea7dwesalejandro.modelo.ComposicionLote;
import com.alejandro.tarea7dwesalejandro.modelo.Ejemplares;
import com.alejandro.tarea7dwesalejandro.modelo.Lote;
import com.alejandro.tarea7dwesalejandro.modelo.Personas;
import com.alejandro.tarea7dwesalejandro.modelo.Plantas;
import com.alejandro.tarea7dwesalejandro.modelo.Proveedor;
import com.alejandro.tarea7dwesalejandro.repositorios.ComposicionLoteRepository;
import com.alejandro.tarea7dwesalejandro.repositorios.EjemplaresRepository;
import com.alejandro.tarea7dwesalejandro.repositorios.LoteRepository;
import com.alejandro.tarea7dwesalejandro.repositorios.PersonasRepository;
import com.alejandro.tarea7dwesalejandro.repositorios.ProveedorRepository;

@Service
public class ServiciosLotes {

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private PersonasRepository personasRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;
    
    @Autowired
    private EjemplaresRepository ejemplaresRepository;
    
    @Autowired
    private ComposicionLoteRepository composicionLoteRepository;

//    public List<Lote> listarLotesPendientes() {
//        return loteRepository.findByFechaHoraRecepcionIsNull();
//    }
    
    public List<Lote> listarLotesPendientes() {
        return loteRepository.findByEstado("NUEVO");
    }


//    public List<Lote> listarLotesRecibidos() {
//        return loteRepository.findByFechaHoraRecepcionIsNotNull();
//    }

    @Transactional
    public Lote registrarLote(Long idProveedor, Long idPersona, boolean urgente) {
        Proveedor proveedor = proveedorRepository.findById(idProveedor)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        Personas persona = personasRepository.findById(idPersona)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        Lote lote = new Lote();
        lote.setProveedor(proveedor);
        lote.setPersonaSolicitante(persona);
        lote.setUrgente(urgente);
        lote.setFechaHoraPeticion(LocalDateTime.now());
        lote.setFechaHoraRecepcion(null);

        return loteRepository.save(lote);
    }

    public Optional<Lote> buscarPorId(Long id) {
        return loteRepository.findById(id);
    }
    
    public void guardar(Lote lote) {
        loteRepository.save(lote);
    }
    
    public List<Lote> obtenerLotesDeProveedor(Proveedor proveedor) {
        return loteRepository.findByProveedorOrderByFechaHoraPeticionDesc(proveedor);
    }
    
    public List<Lote> obtenerLotesDelProveedor(String usuario) {
        Proveedor proveedor = proveedorRepository.findByCredenciales_Usuario(usuario)
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        List<Lote> lotes = loteRepository.findByProveedorConItems(proveedor);

        for (Lote lote : lotes) {
            lote.getItems().size();
        }

        return lotes;
    }


    @Transactional
    public boolean cancelarLoteSiEsPosible(Long idLote, Proveedor proveedor) {
        Optional<Lote> optionalLote = loteRepository.findById(idLote);
        if (optionalLote.isEmpty()) return false;

        Lote lote = optionalLote.get();

        if (!lote.getProveedor().getId().equals(proveedor.getId())) return false;
        if (lote.getEstado().equals("TERMINADO") || lote.getEstado().equals("CANCELADO")) return false;

        lote.setEstado("CANCELADO");
        loteRepository.save(lote);
        return true;
    }
    
//    @Transactional
//    public void recepcionarLote(Long idLote) {
//        Lote lote = loteRepository.findById(idLote)
//                .orElseThrow(() -> new IllegalArgumentException("Lote no encontrado"));
//
//        if (lote.getEstado().equals("CANCELADO")) {
//            throw new IllegalStateException("No se puede recepcionar un lote cancelado");
//        }
//
//        if (lote.getEstado().equals("TERMINADO")) {
//            throw new IllegalStateException("Este lote ya fue recepcionado");
//        }
//
//        lote.setEstado("TERMINADO");
//        lote.setFechaHoraRecepcion(LocalDateTime.now());
//        loteRepository.save(lote);
//    }
    
    @Transactional
    public void recepcionarLote(Long idLote, Long idPersona) {
        Lote lote = loteRepository.findById(idLote)
                .orElseThrow(() -> new IllegalArgumentException("Lote no encontrado"));

        String estado = lote.getEstado();
        if (estado == null) {
            throw new IllegalStateException("El lote no tiene un estado definido");
        }

        if (estado.equals("CANCELADO")) {
            throw new IllegalStateException("No se puede recepcionar un lote cancelado");
        }

        if (estado.equals("TERMINADO") || lote.getFechaHoraRecepcion() != null) {
            throw new IllegalStateException("Este lote ya fue recepcionado");
        }
        Personas persona = personasRepository.findById(idPersona)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        lote.setPersonaReceptora(persona);

        lote.setEstado("TERMINADO");
        lote.setFechaHoraRecepcion(LocalDateTime.now());
        loteRepository.save(lote);

        // Obtener las composiciones del lote
        List<ComposicionLote> composiciones = composicionLoteRepository.findByLote(lote);

        for (ComposicionLote comp : composiciones) {
            Plantas planta = comp.getPlanta();
            int cantidad = comp.getCantidad();

            // Contar cuántos ejemplares hay ya de esa planta
            long existentes = ejemplaresRepository.countByPlanta(planta);

            for (int i = 1; i <= cantidad; i++) {
                Ejemplares ejemplar = new Ejemplares();
                ejemplar.setPlanta(planta);
                ejemplar.setNombre(planta.getCodigo() + "_" + (existentes + i));
                ejemplar.setDisponible(true);
                ejemplar.setLote(lote);
                ejemplaresRepository.save(ejemplar);
            }
        }
    }

    public List<Lote> listarLotesRecibidos() {
        return loteRepository.findByEstadoOrderByFechaHoraRecepcionDesc("TERMINADO");
    }

    

}
