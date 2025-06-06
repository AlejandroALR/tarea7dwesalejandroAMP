package com.alejandro.tarea7dwesalejandro.controladores;

import com.alejandro.tarea7dwesalejandro.dto.FiltrarMensajeDTO;
import com.alejandro.tarea7dwesalejandro.dto.RegistroMensajeDTO;
import com.alejandro.tarea7dwesalejandro.modelo.Mensajes;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosEjemplares;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosMensajes;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosPersonas;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosPlantas;

import jakarta.validation.Valid;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mensajes")
public class MensajesController {

    @Autowired
    private ServiciosMensajes serviciosMensajes;
    
    @Autowired
    private ServiciosEjemplares serviciosEjemplares;
    
    @Autowired
    private ServiciosPersonas serviciosPersonas;
    
    @Autowired
    private ServiciosPlantas serviciosPlantas;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("mensajes", serviciosMensajes.listarTodos());
        return "mensajes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("mensaje", new Mensajes());
        return "mensajes/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Mensajes mensaje) {
        serviciosMensajes.guardar(mensaje);
        return "redirect:/mensajes";
    }
    
	@GetMapping("/gestionMensajes")
	public String gestionMensajes() {
		return "/mensajes/gestionMensajes"; // gestionMensajes.html
	}
	
	@GetMapping("/registrarMensaje")
	public String mostrarFormulario(Model model) {
	    model.addAttribute("ejemplares", serviciosEjemplares.listarTodos());
	    model.addAttribute("registro", new RegistroMensajeDTO());
	    return "mensajes/registrarMensaje";
	}

	@PostMapping("/registrarMensaje")
	public String registrarMensaje(@Valid @ModelAttribute("registro") RegistroMensajeDTO dto,
	                               BindingResult result,
	                               Model model,
	                               RedirectAttributes redirectAttributes,
	                               Authentication auth) {

	    if (result.hasErrors()) {
	        model.addAttribute("ejemplares", serviciosEjemplares.listarTodos());
	        return "mensajes/registrarMensaje";
	    }

	    Long idPersona = serviciosPersonas.obtenerIdDesdeAuth(auth); 
	    serviciosMensajes.guardarMensaje(dto, idPersona);

	    redirectAttributes.addFlashAttribute("mensaje", "Mensaje registrado correctamente.");
	    return "redirect:/mensajes/registrarMensaje";
	}
	
	@GetMapping("/filtrarMensaje")
	public String mostrarFormularioFiltrar(Model model) {
	    model.addAttribute("filtro", new FiltrarMensajeDTO());
	    model.addAttribute("personas", serviciosPersonas.listarTodas());
	    model.addAttribute("plantas", serviciosPlantas.listarTodas());
	    model.addAttribute("resultados", Collections.emptyList()); // lista vacía al cargar
	    return "mensajes/filtrarMensaje";
	}

	@PostMapping("/filtrarMensaje")
	public String procesarFiltro(@ModelAttribute("filtro") FiltrarMensajeDTO filtro,
	                             Model model) {

	    if (filtro.getIdPersona() != null && filtro.getIdPersona().toString().isBlank()) {
	        filtro.setIdPersona(null);
	    }
	    if (filtro.getCodigoPlanta() != null && filtro.getCodigoPlanta().isBlank()) {
	        filtro.setCodigoPlanta(null);
	    }

	    List<Mensajes> resultados = serviciosMensajes.filtrarMensajes(
	        filtro.getIdPersona(),
	        filtro.getCodigoPlanta(),
	        filtro.getFechaDesde(),
	        filtro.getFechaHasta()
	    );

	    model.addAttribute("filtro", filtro);
	    model.addAttribute("personas", serviciosPersonas.listarTodas());
	    model.addAttribute("plantas", serviciosPlantas.listarTodas());
	    model.addAttribute("resultados", resultados);
	    return "mensajes/filtrarMensaje";
	}



}
