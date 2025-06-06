package com.alejandro.tarea7dwesalejandro.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alejandro.tarea7dwesalejandro.dto.ModificarPlantaDTO;
import com.alejandro.tarea7dwesalejandro.dto.RegistroPlantaDTO;
import com.alejandro.tarea7dwesalejandro.modelo.Plantas;
import com.alejandro.tarea7dwesalejandro.repositorios.PlantasRepository;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosPlantas;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/plantas")
public class PlantasController {

    @Autowired
    private ServiciosPlantas serviciosplantas;
    
    @Autowired
    private PlantasRepository plantasRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("plantas", serviciosplantas.listarTodas());
        return "plantas/lista";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("planta", new Plantas());
        return "plantas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Plantas planta) {
    	serviciosplantas.guardarOActualizar(planta);
        return "redirect:/plantas";
    }

    @GetMapping("/eliminar/{codigo}")
    public String eliminar(@PathVariable String codigo) {
    	serviciosplantas.eliminarPorCodigo(codigo);
        return "redirect:/plantas";
    }

	@GetMapping("/gestionPlantas")
	public String gestionPlantas() {
		return "/plantas/gestionPlantas"; // gestionPlantas.html
	}
	
	@GetMapping("/verPlantas")
	public String verPlantas(Model model) {
	    model.addAttribute("plantas", serviciosplantas.listarTodas());
	    return "/plantas/verPlantas"; // verPlantas.html
	}
    
    @GetMapping("/registrarPlanta")
    public String mostrarFormularioPlanta(Model model) {
        model.addAttribute("planta", new RegistroPlantaDTO());
        model.addAttribute("plantas", serviciosplantas.listarTodas());
        return "plantas/registrarPlanta";
    }
    
    @GetMapping("/verPlantasInvi")
    public String verPlantasInvi(Model model) {
        model.addAttribute("plantas", serviciosplantas.listarTodas());
        return "plantas/verPlantasInvi";
    }

    @PostMapping("/registrarPlanta")
    public String procesarRegistroPlanta(@Valid @ModelAttribute("planta") RegistroPlantaDTO dto,
                                         BindingResult result,
                                         Model model,
                                         RedirectAttributes redirectAttributes) {

        if (serviciosplantas.codigoExiste(dto.getCodigo())) {
            result.rejectValue("codigo", "error.codigo", "El código ya está registrado.");
        }

        if (result.hasErrors()) {
            model.addAttribute("plantas", serviciosplantas.listarTodas());
            return "plantas/registrarPlanta";
        }

        serviciosplantas.registrarPlanta(dto); // implementa esto en el servicio
        redirectAttributes.addFlashAttribute("mensaje", "La planta ha sido registrada correctamente.");
        return "redirect:/plantas/registrarPlanta";
    }
    
    @GetMapping("/limpiarFormulario")
    public String limpiarFormulario(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("planta", new Plantas());
        return "redirect:/plantas/registrarPlanta";
    }
    
    @GetMapping("/modificarPlanta")
    public String mostrarFormularioModificar(Model model) {
        model.addAttribute("plantas", serviciosplantas.listarTodas());
        model.addAttribute("plantaSeleccionada", new ModificarPlantaDTO());
        return "plantas/modificarPlanta";
    }

    @GetMapping("/modificarPlanta/cargar")
    public String cargarDatosPlanta(@RequestParam String codigo, Model model) {
        Plantas planta = serviciosplantas.obtenerPorCodigo(codigo);
        ModificarPlantaDTO dto = new ModificarPlantaDTO();
        dto.setCodigo(planta.getCodigo());
        dto.setNombreCientifico(planta.getNombreCientifico());
        dto.setNombreComun(planta.getNombreComun());

        model.addAttribute("plantaSeleccionada", dto);
        model.addAttribute("plantas", serviciosplantas.listarTodas());
        return "plantas/modificarPlanta";
    }

    @PostMapping("/modificarPlanta")
    public String procesarModificacion(@Valid @ModelAttribute("plantaSeleccionada") ModificarPlantaDTO dto,
                                       BindingResult result,
                                       Model model,
                                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("plantas", serviciosplantas.listarTodas());
            return "plantas/modificarPlanta";
        }

        serviciosplantas.actualizarPlanta(dto.getCodigo(), dto.getNombreCientifico(), dto.getNombreComun());
        redirectAttributes.addFlashAttribute("mensaje", "Planta modificada correctamente.");
        return "redirect:/plantas/modificarPlanta";
    }
}

