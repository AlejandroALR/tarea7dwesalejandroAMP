package com.alejandro.tarea7dwesalejandro.controladores;

import com.alejandro.tarea7dwesalejandro.dto.RegistroProveedorDTO;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosProveedores;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminProveedoresController {

    @Autowired
    private ServiciosProveedores serviciosProveedores;

    @GetMapping("/registrarProveedor")
    public String mostrarFormularioRegistroProveedor(Model model) {
        model.addAttribute("registroProveedor", new RegistroProveedorDTO());
        return "proveedores/registrarProveedor";
    }

    @PostMapping("/registrarProveedor")
    public String procesarFormularioRegistroProveedor(
            @ModelAttribute("registroProveedor") @Valid RegistroProveedorDTO proveedorDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (serviciosProveedores.usuarioYaExiste(proveedorDTO.getUsuario())) {
            result.rejectValue("usuario", null, "Ese nombre de usuario ya está en uso");
        }

        if (serviciosProveedores.cifYaExiste(proveedorDTO.getCif())) {
            result.rejectValue("cif", null, "Ya existe un proveedor con ese CIF");
        }

        if (result.hasErrors()) {
            return "proveedores/registrarProveedor";
        }

        try {
            serviciosProveedores.registrarProveedorConCredenciales(proveedorDTO);
            redirectAttributes.addFlashAttribute("exito", "Proveedor registrado correctamente.");
            return "redirect:/admin";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorCif", e.getMessage());
            return "proveedores/registrarProveedor";
        }
    }
}
