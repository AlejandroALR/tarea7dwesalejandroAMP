package com.alejandro.tarea7dwesalejandro.controladores;

import com.alejandro.tarea7dwesalejandro.modelo.ComposicionLote;
import com.alejandro.tarea7dwesalejandro.modelo.ItemLote;
import com.alejandro.tarea7dwesalejandro.modelo.Lote;
import com.alejandro.tarea7dwesalejandro.modelo.Proveedor;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosComposicionLote;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosLotes;
import com.alejandro.tarea7dwesalejandro.repositorios.CredencialesRepository;
import com.alejandro.tarea7dwesalejandro.repositorios.ProveedorRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/proveedor")
@PreAuthorize("hasRole('PROVEEDOR')")
public class LotesProveedorController {

    @Autowired
    private ServiciosLotes serviciosLotes;

    @Autowired
    private CredencialesRepository credencialesRepository;
    
    @Autowired
    private ServiciosComposicionLote serviciosComposicionLote;
    
    @Autowired
    private ProveedorRepository proveedorRepository;

//    @GetMapping("/misLotes")
//    public String verMisLotes(Model model, HttpSession session) {
//        Credenciales cred = (Credenciales) session.getAttribute("credenciales");
//        if (cred == null || cred.getProveedor() == null) {
//            return "redirect:/login";
//        }
//
//        List<Lote> lotes = serviciosLotes.obtenerLotesDeProveedor(cred.getProveedor());
//        model.addAttribute("lotes", lotes);
//        return "proveedores/verMisLotes";
//    }
    
    @GetMapping("/misLotes")
    public String verMisLotes(Model model, Authentication auth) {
        String username = auth.getName();
        List<Lote> lotes = serviciosLotes.obtenerLotesDelProveedor(username);
        model.addAttribute("lotes", lotes);
        return "proveedores/verMisLotes";
    }

    @PostMapping("/cancelarLote")
    public String cancelarLote(@RequestParam("idLote") Long idLote,
                               RedirectAttributes redirectAttributes,
                               Authentication auth) {

        Proveedor proveedor = proveedorRepository.findByCredenciales_Usuario(auth.getName())
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        boolean cancelado = serviciosLotes.cancelarLoteSiEsPosible(idLote, proveedor);

        if (cancelado) {
            redirectAttributes.addFlashAttribute("exito", "Lote cancelado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se puede cancelar el lote.");
        }

        return "redirect:/proveedor/misLotes";
    }
    
    @GetMapping("/pendientes")
    public String mostrarPendientesRecepcion(Model model) {
        List<Lote> pendientes = serviciosLotes.listarLotesPendientes();

        Map<Long, List<ComposicionLote>> composiciones = new HashMap<>();
        for (Lote lote : pendientes) {
            composiciones.put(lote.getId(), serviciosComposicionLote.obtenerPorLote(lote));
        }

        model.addAttribute("pendientes", pendientes);
        model.addAttribute("composiciones", composiciones);
        return "lotes/pendientesRecepcion";
    }



    
    
    
}
