package com.alejandro.tarea7dwesalejandro.controladores;

import com.alejandro.tarea7dwesalejandro.dto.RegistroPersonaDTO;
import com.alejandro.tarea7dwesalejandro.modelo.Personas;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosCredenciales;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosPersonas;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/personas")
public class PersonasController {

	@Autowired
	private ServiciosPersonas serviciosPersonas;
	
	@Autowired
	private ServiciosCredenciales serviciosCredenciales;

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("personas", serviciosPersonas.listarTodas());
		return "personas/lista";
	}

	@GetMapping("/nueva")
	public String nueva(Model model) {
		model.addAttribute("persona", new Personas());
		return "personas/formulario";
	}

	@PostMapping("/guardar")
	public String guardar(@ModelAttribute Personas persona) {
		serviciosPersonas.guardar(persona);
		return "redirect:/personas";
	}

	@GetMapping("/registrar")
	public String mostrarFormularioRegistro(Model model) {
		model.addAttribute("registro", new RegistroPersonaDTO());
		return "personas/registrarPersona"; // registrarPersona.html
	}

	@PostMapping("/registrar")
	public String procesarRegistro(@Valid @ModelAttribute("registro") RegistroPersonaDTO dto,
	                                BindingResult result,
	                                Model model,
	                                RedirectAttributes redirectAttributes) {

	    // Validaciones personalizadas añadidas al BindingResult
	    if (serviciosPersonas.emailExiste(dto.getEmail())) {
	        result.rejectValue("email", "error.email", "El correo ya está registrado.");
	    }

	    if (serviciosCredenciales.usuarioExiste(dto.getUsuario())) {
	        result.rejectValue("usuario", "error.usuario", "El nombre de usuario ya existe.");
	    }

	    // Aquí ya se recogen tanto errores del DTO como los personalizados
	    if (result.hasErrors()) {
	        return "personas/registrarPersona";
	    }

	    serviciosPersonas.registrarPersonaConCredenciales(dto);
	    redirectAttributes.addFlashAttribute("mensaje", "La persona ha sido registrada correctamente.");
	    return "redirect:/personas/registrar";

	}



}
