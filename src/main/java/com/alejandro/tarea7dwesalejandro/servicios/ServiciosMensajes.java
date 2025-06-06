package com.alejandro.tarea7dwesalejandro.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alejandro.tarea7dwesalejandro.dto.RegistroMensajeDTO;
import com.alejandro.tarea7dwesalejandro.modelo.Mensajes;
import com.alejandro.tarea7dwesalejandro.repositorios.EjemplaresRepository;
import com.alejandro.tarea7dwesalejandro.repositorios.MensajesRepository;
import com.alejandro.tarea7dwesalejandro.repositorios.PersonasRepository;

@Service
public class ServiciosMensajes {

	@Autowired
	private MensajesRepository mensajesRepository;

	@Autowired
	private EjemplaresRepository ejemplaresRepository;

	@Autowired
	private PersonasRepository personasRepository;

	public Mensajes guardar(Mensajes mensaje) {
		return mensajesRepository.save(mensaje);
	}

	public List<Mensajes> listarTodos() {
		return mensajesRepository.findAll();
	}

	public List<Mensajes> buscarPorEjemplar(Long idEjemplar) {
		return mensajesRepository.findByEjemplarId(idEjemplar);
	}

	public List<Mensajes> buscarEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
		return mensajesRepository.findByFechaBetween(inicio, fin);
	}

	public int contarPorEjemplar(Long idEjemplar) {
		return mensajesRepository.contarMensajesPorEjemplar(idEjemplar);
	}

	public Optional<LocalDateTime> obtenerUltimaFechaPorEjemplar(Long idEjemplar) {
		return mensajesRepository.obtenerUltimaFechaMensaje(idEjemplar);
	}

	public List<Mensajes> buscarPorNombrePersona(String nombre) {
		return mensajesRepository.findByNombrePersona(nombre);
	}

	public List<Mensajes> buscarPorCodigoPlanta(String codigoPlanta) {
		return mensajesRepository.findByCodigoPlanta(codigoPlanta);
	}

	public void guardarMensaje(RegistroMensajeDTO dto, Long idPersona) {
		Mensajes mensaje = new Mensajes();
		mensaje.setMensaje(dto.getMensaje());
		mensaje.setFecha(LocalDateTime.now());
		mensaje.setEjemplar(ejemplaresRepository.findById(dto.getIdEjemplar()).orElseThrow());
		mensaje.setPersona(personasRepository.findById(idPersona).orElseThrow());
		mensajesRepository.save(mensaje);
	}
	
	public List<Mensajes> filtrarMensajes(Long idPersona, String codigoPlanta, LocalDate desde, LocalDate hasta) {
	    return mensajesRepository.findAll().stream()
	        .filter(m -> {
	            if (idPersona != null) {
	                if (m.getPersona() == null || m.getPersona().getId() == null) return false;
	                if (!m.getPersona().getId().equals(idPersona)) return false;
	            }
	            return true;
	        })
	        .filter(m -> {
	            if (codigoPlanta != null) {
	                if (m.getEjemplar() == null || m.getEjemplar().getPlanta() == null) return false;
	                if (!m.getEjemplar().getPlanta().getCodigo().equals(codigoPlanta)) return false;
	            }
	            return true;
	        })
	        .filter(m -> {
	            if (m.getFecha() == null) return false;
	            LocalDate fecha = m.getFecha().toLocalDate();
	            if (desde != null && fecha.isBefore(desde)) return false;
	            if (hasta != null && fecha.isAfter(hasta)) return false;
	            return true;
	        })
	        .toList();
	}
	
	public String obtenerPersonaReceptoraPorLote(Long idLote) {
	    return mensajesRepository.findFirstByEjemplar_Lote_Id(idLote)
	           .map(m -> m.getPersona().getNombre())
	           .orElse("Desconocido");
	}


	
//	public List<Mensajes> filtrarMensajes(Long idPersona, String codigoPlanta, LocalDate desde, LocalDate hasta) {
//	    return mensajesRepository.findAll().stream()
//	        .filter(m -> m.getPersona() != null &&
//	                     (idPersona == null || m.getPersona().getId().equals(idPersona)))
//	        .filter(m -> m.getEjemplar() != null && m.getEjemplar().getPlanta() != null &&
//	                     (codigoPlanta == null || m.getEjemplar().getPlanta().getCodigo().equals(codigoPlanta)))
//	        .filter(m -> {
//	            if (m.getFecha() == null) return false;
//	            LocalDate fechaMensaje = m.getFecha().toLocalDate();
//	            return (desde == null || !fechaMensaje.isBefore(desde)) &&
//	                   (hasta == null || !fechaMensaje.isAfter(hasta));
//	        })
//	        .toList();
//	}

	

//	public List<Mensajes> filtrarMensajes(Long idPersona, String codigoPlanta, LocalDate desde, LocalDate hasta) {
//
//		return mensajesRepository.findAll().stream()
//				.filter(m -> idPersona == null || m.getPersona().getId().equals(idPersona))
//				.filter(m -> codigoPlanta == null || m.getEjemplar().getPlanta().getCodigo().equals(codigoPlanta))
//				.filter(m -> {
//					LocalDate fechaMensaje = m.getFecha().toLocalDate();
//					boolean desdeOK = desde == null || !fechaMensaje.isBefore(desde);
//					boolean hastaOK = hasta == null || !fechaMensaje.isAfter(hasta);
//					return desdeOK && hastaOK;
//				}).toList();
//	}

}
