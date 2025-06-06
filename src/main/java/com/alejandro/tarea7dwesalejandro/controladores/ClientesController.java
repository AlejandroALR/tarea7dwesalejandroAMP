package com.alejandro.tarea7dwesalejandro.controladores;

import com.alejandro.tarea7dwesalejandro.dto.RegistroClienteDTO;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosClientes;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosPlantas;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClientesController {

    @Autowired
    private ServiciosClientes serviciosClientes;
    
    @Autowired
    private ServiciosPlantas serviciosPlantas;

    @GetMapping("/clientes/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("cliente", new RegistroClienteDTO());
        return "clientes/registroClientes";
    }

    @PostMapping("/clientes/registro")
    public String registrarCliente(@ModelAttribute("cliente") @Valid RegistroClienteDTO clienteDTO,
                                   BindingResult result,
                                   RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "clientes/registroClientes";
        }

        serviciosClientes.registrarCliente(clienteDTO);
        redirectAttrs.addFlashAttribute("mensaje", "Cliente registrado correctamente");
        return "redirect:/login";
    }
    
    @GetMapping("/clientes/verPlantasClie")
    public String verPlantasClie(Model model) {
        model.addAttribute("plantas", serviciosPlantas.listarTodas());
        return "clientes/verPlantasClie";
    }
}

