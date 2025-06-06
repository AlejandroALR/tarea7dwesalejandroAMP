package com.alejandro.tarea7dwesalejandro.controladores;

import com.alejandro.tarea7dwesalejandro.modelo.ComposicionLote;
import com.alejandro.tarea7dwesalejandro.modelo.Ejemplares;
import com.alejandro.tarea7dwesalejandro.modelo.Lote;
import com.alejandro.tarea7dwesalejandro.modelo.Mensajes;
import com.alejandro.tarea7dwesalejandro.modelo.Personas;
import com.alejandro.tarea7dwesalejandro.servicios.*;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/personal/recepcionLotes")
@PreAuthorize("hasAnyRole('PERSONAL', 'ADMIN')")
public class RecepcionLotesController {

    @Autowired
    private ServiciosLotes serviciosLotes;

    @Autowired
    private ServiciosComposicionLote serviciosComposicionLote;

    @Autowired
    private ServiciosEjemplares serviciosEjemplares;

    @Autowired
    private ServiciosMensajes serviciosMensajes;

    @Autowired
    private ServiciosPersonas serviciosPersonas;

    @GetMapping
    public String verPendientes(Model model) {
        List<Lote> pendientes = serviciosLotes.listarLotesPendientes();
        model.addAttribute("pendientes", pendientes);
        return "lotes/pendientesRecepcion";
    }

//    @PostMapping("/confirmar")
//    @Transactional
//    public String confirmarRecepcion(@RequestParam Long idLote, Authentication auth) {
//        Lote lote = serviciosLotes.buscarPorId(idLote)
//                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));
//
//        String username = auth.getName();
//        Personas receptor = serviciosPersonas.buscarPorUsuario(username)
//                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
//
//        lote.setFechaHoraRecepcion(LocalDateTime.now());
//        serviciosLotes.guardar(lote);
//
//        List<ComposicionLote> composicion = serviciosComposicionLote.obtenerPorLote(lote);
//
//        for (ComposicionLote comp : composicion) {
//        	long existentes = serviciosEjemplares.contarPorPlanta(comp.getPlanta());
//        	for (int i = 1; i <= comp.getCantidad(); i++) {
//        	    Ejemplares ej = new Ejemplares();
//        	    ej.setPlanta(comp.getPlanta());
//        	    ej.setNombre(comp.getPlanta().getCodigo() + "_" + (existentes + i));
//        	    ej.setLote(lote);
//        	    Ejemplares guardado = serviciosEjemplares.guardar(ej);
//
//        	    Mensajes m = new Mensajes();
//        	    m.setEjemplar(guardado);
//        	    m.setPersona(receptor);
//        	    m.setFecha(LocalDateTime.now());
//        	    m.setMensaje("Ejemplar " + ej.getNombre() + " recibido el " +
//        	            m.getFecha().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
//        	            " en el lote " + lote.getId() + " del proveedor " +
//        	            lote.getProveedor().getNombre() + " solicitado por " +
//        	            lote.getPersonaSolicitante().getNombre() + " y confirmado por " +
//        	            receptor.getNombre());
//
//        	    serviciosMensajes.guardar(m);
//        	}
//        }
//
//        return "redirect:/personal/recepcionLotes";
//    }

//    @GetMapping("/recibidos")
//    public String verRecibidos(Model model) {
//        List<Lote> recibidos = serviciosLotes.listarLotesRecibidos();
//        model.addAttribute("recibidos", recibidos);
//        return "lotes/lotesRecibidos";
//    }
}

