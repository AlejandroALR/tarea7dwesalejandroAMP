package com.alejandro.tarea7dwesalejandro.servicios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alejandro.tarea7dwesalejandro.dto.RegistroEjemplarDTO;
import com.alejandro.tarea7dwesalejandro.modelo.Ejemplares;
import com.alejandro.tarea7dwesalejandro.modelo.Lote;
import com.alejandro.tarea7dwesalejandro.modelo.Mensajes;
import com.alejandro.tarea7dwesalejandro.modelo.Plantas;
import com.alejandro.tarea7dwesalejandro.repositorios.EjemplaresRepository;
import com.alejandro.tarea7dwesalejandro.repositorios.MensajesRepository;
import com.alejandro.tarea7dwesalejandro.repositorios.PersonasRepository;
import com.alejandro.tarea7dwesalejandro.repositorios.PlantasRepository;

@Service
@Transactional
public class ServiciosEjemplares {

    @Autowired
    private EjemplaresRepository ejemplaresRepository;
    
    @Autowired
    private PersonasRepository personasRepository;
    
    @Autowired
    private PlantasRepository plantasRepository;
    
    @Autowired
    private MensajesRepository mensajesRepository;

    public Ejemplares guardar(Ejemplares ej) {
        return ejemplaresRepository.save(ej);
    }

    public Optional<Ejemplares> buscarPorId(Long id) {
        return ejemplaresRepository.findById(id);
    }

    public Optional<Ejemplares> buscarPorNombre(String nombre) {
        return ejemplaresRepository.findByNombre(nombre);
    }

    public List<Ejemplares> listarTodos() {
        return ejemplaresRepository.findAll();
    }

    public List<Ejemplares> buscarPorTipo(String tipoCodigo) {
        return ejemplaresRepository.findByTipo(tipoCodigo);
    }

    public void eliminar(Long id) {
        ejemplaresRepository.deleteById(id);
    }

    public int contarPorTipo(String tipoCodigo) {
        return ejemplaresRepository.findByTipo(tipoCodigo).size();
    }

    public Long obtenerUltimoId() {
        return ejemplaresRepository.findLastId();
    }
    
    @Transactional
    public void registrarEjemplarConMensajeInicial(RegistroEjemplarDTO dto, Long idPersona) {
        // Buscar la planta
        var planta = plantasRepository.findById(dto.getCodigoPlanta())
                .orElseThrow(() -> new RuntimeException("Planta no encontrada"));

        // Contar cuántos ejemplares hay ya de esa planta
        long contador = ejemplaresRepository.countByPlanta(planta);

        // Crear el ejemplar
        Ejemplares nuevo = new Ejemplares();
        nuevo.setPlanta(planta);
        nuevo.setNombre(planta.getCodigo() + "_" + (contador + 1));
        nuevo.setDisponible(true);

        ejemplaresRepository.save(nuevo);

        // Obtener persona autenticada
        var persona = personasRepository.findById(idPersona)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        // Crear el mensaje automático
        Mensajes mensaje = new Mensajes();
        mensaje.setEjemplar(nuevo);
        mensaje.setPersona(persona);
        mensaje.setFecha(LocalDateTime.now());
        mensaje.setMensaje(nuevo.getNombre() + " ha sido creada por " + persona.getNombre());

        mensajesRepository.save(mensaje);
    }

    
//    public void registrarEjemplarConMensajeInicial(RegistroEjemplarDTO dto, Long idPersona) {
//
//        // 1. Obtener planta
//        Plantas planta = plantasRepository.findByCodigo(dto.getCodigoPlanta())
//                .orElseThrow(() -> new RuntimeException("Planta no encontrada"));
//
//        // 2. Calcular número de ejemplares existentes de esa planta
//        int numExistentes = ejemplaresRepository.countByPlanta_Codigo(planta.getCodigo());
//        String nombreGenerado = planta.getCodigo() + "_" + (numExistentes + 1);
//
//        // 3. Obtener persona
//        Personas persona = personasRepository.findById(idPersona)
//                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
//
//        // 4. Crear ejemplar con datos completos
//        Ejemplares ejemplar = new Ejemplares();
//        ejemplar.setNombre(nombreGenerado);
//        ejemplar.setPlanta(planta);
//        ejemplaresRepository.save(ejemplar);
//
//        // 5. Crear mensaje inicial
//        Mensajes mensaje = new Mensajes();
//        mensaje.setMensaje(dto.getMensajeInicial());
//        mensaje.setFecha(LocalDateTime.now());
//        mensaje.setEjemplar(ejemplar);
//        mensaje.setPersona(persona);
//        mensajesRepository.save(mensaje);
//    }


    @Transactional(readOnly = true)
    public List<Ejemplares> buscarEjemplaresPorPlantas(List<String> codigosPlantas) {
        if (codigosPlantas == null || codigosPlantas.isEmpty()) {
            return ejemplaresRepository.findAll();
        } else {
        	return ejemplaresRepository.findWithMensajesByPlantaCodigoIn(codigosPlantas);
        }
    }
    
    public List<Object[]> obtenerStockPorPlanta() {
        return ejemplaresRepository.contarEjemplaresDisponiblesPorPlanta();
    }
    
    public long contarPorPlanta(Plantas planta) {
        return ejemplaresRepository.countByPlanta(planta);
    }

    public List<Ejemplares> obtenerPorLote(Lote lote) {
        return ejemplaresRepository.findByLote(lote);
    }


}

