package com.alejandro.tarea7dwesalejandro.controladores;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alejandro.tarea7dwesalejandro.dto.EjemplarResumenDTO;
import com.alejandro.tarea7dwesalejandro.dto.FiltroEjemplarDTO;
import com.alejandro.tarea7dwesalejandro.dto.RegistroEjemplarDTO;
import com.alejandro.tarea7dwesalejandro.modelo.Ejemplares;
import com.alejandro.tarea7dwesalejandro.modelo.Mensajes;
import com.alejandro.tarea7dwesalejandro.repositorios.EjemplaresRepository;
import com.alejandro.tarea7dwesalejandro.repositorios.MensajesRepository;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosEjemplares;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosPersonas;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosPlantas;

@Controller
@RequestMapping("/ejemplares")
public class EjemplaresController {

    @Autowired
    private ServiciosEjemplares serviciosEjemplares;
    
    @Autowired
    private ServiciosPlantas serviciosPlantas;
    
    @Autowired
    private ServiciosPersonas serviciosPersonas;
    
    @Autowired
    private EjemplaresRepository ejemplaresRepository;
    
    @Autowired
    private MensajesRepository mensajesRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ejemplares", serviciosEjemplares.listarTodos());
        return "ejemplares/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("ejemplar", new Ejemplares());
        return "ejemplares/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Ejemplares ejemplar) {
        serviciosEjemplares.guardar(ejemplar);
        return "redirect:/ejemplares";
    }
    
	@GetMapping("/gestionEjemplares")
	public String gestionEjemplares() {
		return "/ejemplares/gestionEjemplares"; // gestionEjemplares.html
	}
	
	@GetMapping("/registrar")
	public String mostrarFormularioRegistroEjemplar(Model model) {
	    model.addAttribute("ejemplar", new RegistroEjemplarDTO());
	    model.addAttribute("plantas", serviciosPlantas.listarTodas());
	    return "ejemplares/registrarEjemplar";
	}

	@PostMapping("/registrar")
	public String procesarRegistroEjemplar(@Valid @ModelAttribute("ejemplar") RegistroEjemplarDTO dto,
	                                       BindingResult result,
	                                       Model model,
	                                       RedirectAttributes redirectAttributes,
	                                       Authentication auth) {
	    if (result.hasErrors()) {
	        model.addAttribute("plantas", serviciosPlantas.listarTodas());
	        return "ejemplares/registrarEjemplar";
	    }

	    Long idPersona = serviciosPersonas.obtenerIdDesdeAuth(auth);
	    serviciosEjemplares.registrarEjemplarConMensajeInicial(dto, idPersona);

	    redirectAttributes.addFlashAttribute("mensaje", "Ejemplar registrado correctamente.");
	    return "redirect:/ejemplares/registrar";
	}


	
	@GetMapping("/filtrarEjemplares")
	public String mostrarFormularioFiltrado(Model model) {
	    model.addAttribute("filtro", new FiltroEjemplarDTO());
	    model.addAttribute("plantas", serviciosPlantas.listarTodas());
	    model.addAttribute("resultados", Collections.emptyList());
	    return "ejemplares/filtrarEjemplares";
	}

	@PostMapping("/filtrarEjemplares")
	public String procesarFiltroEjemplares(@ModelAttribute("filtro") FiltroEjemplarDTO filtro,
	                                       Model model) {
	    List<EjemplarResumenDTO> resultados = new ArrayList<>();

	    List<Ejemplares> ejemplares = serviciosEjemplares.buscarEjemplaresPorPlantas(filtro.getCodigosPlantas());

	    for (Ejemplares ej : ejemplares) {
	        String codigoEjemplar = ej.getNombre();
	        String nombrePlanta = ej.getPlanta().getNombreComun();
	        List<Mensajes> mensajes = mensajesRepository.findByEjemplarOrderByFechaDesc(ej);
	        int totalMensajes = mensajes.size();
	        LocalDateTime ultimaFecha = totalMensajes > 0 ? mensajes.get(0).getFecha() : null;

	        EjemplarResumenDTO dto = new EjemplarResumenDTO(
	            codigoEjemplar,
	            nombrePlanta,
	            totalMensajes,
	            ultimaFecha
	        );
	        resultados.add(dto);
	    }

	    model.addAttribute("filtro", filtro);
	    model.addAttribute("plantas", serviciosPlantas.listarTodas());
	    model.addAttribute("resultados", resultados);
	    return "ejemplares/filtrarEjemplares";
	}

	
	@GetMapping("/verMensajes")
	public String verMensajesEjemplar(@RequestParam(name = "id", required = false) Long id, Model model) {
	    if (id == null) {
	        return "redirect:/ejemplares/filtrarEjemplares";
	    }

	    Ejemplares ejemplar = ejemplaresRepository.findByIdConMensajesYPersonas(id)
	        .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado"));

	    List<Mensajes> mensajesOrdenados = ejemplar.getMensajes().stream()
	        .sorted((m1, m2) -> m1.getFecha().compareTo(m2.getFecha()))
	        .toList();

	    model.addAttribute("ejemplar", ejemplar);
	    model.addAttribute("mensajes", mensajesOrdenados);

	    return "ejemplares/verMensajes";
	}
	
	@GetMapping("/listarMensajes")
	public String listarMensajes(Model model) {
	    List<EjemplarResumenDTO> resumenes = new ArrayList<>();

	    List<Ejemplares> ejemplares = ejemplaresRepository.findAll();

	    for (Ejemplares ej : ejemplares) {
	        String codigoEjemplar = ej.getNombre();
	        String nombrePlanta = ej.getPlanta().getNombreComun();
	        List<Mensajes> mensajes = mensajesRepository.findByEjemplarOrderByFechaDesc(ej);
	        int totalMensajes = mensajes.size();
	        LocalDateTime ultimaFecha = totalMensajes > 0 ? mensajes.get(0).getFecha() : null;

	        EjemplarResumenDTO dto = new EjemplarResumenDTO(
	            codigoEjemplar,
	            nombrePlanta,
	            totalMensajes,
	            ultimaFecha
	        );
	        resumenes.add(dto);
	    }

	    model.addAttribute("resultados", resumenes);
	    return "ejemplares/listarMensajes";
	}
	
	@GetMapping("/personal/stock")
	public String verStock(Model model) {
	    List<Object[]> stockPorPlanta = serviciosEjemplares.obtenerStockPorPlanta();
	    model.addAttribute("stock", stockPorPlanta);
	    return "ejemplares/verStock";
	}

}

