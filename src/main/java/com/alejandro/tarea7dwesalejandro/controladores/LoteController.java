package com.alejandro.tarea7dwesalejandro.controladores;

import com.alejandro.tarea7dwesalejandro.dto.ItemLoteDTO;
import com.alejandro.tarea7dwesalejandro.modelo.ComposicionLote;
import com.alejandro.tarea7dwesalejandro.modelo.Ejemplares;
import com.alejandro.tarea7dwesalejandro.modelo.Lote;
import com.alejandro.tarea7dwesalejandro.modelo.Mensajes;
import com.alejandro.tarea7dwesalejandro.modelo.Personas;
import com.alejandro.tarea7dwesalejandro.modelo.Plantas;
import com.alejandro.tarea7dwesalejandro.repositorios.CredencialesRepository;
import com.alejandro.tarea7dwesalejandro.repositorios.PersonasRepository;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosComposicionLote;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosEjemplares;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosLotes;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosMensajes;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosPersonas;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosPlantas;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosProveedores;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/lotes")
@PreAuthorize("hasRole('PERSONAL')")
@Validated
public class LoteController {

    @Autowired
    private ServiciosLotes serviciosLotes;

    @Autowired
    private ServiciosPlantas serviciosPlantas;

    @Autowired
    private ServiciosComposicionLote serviciosComposicionLote;

    @Autowired
    private ServiciosProveedores serviciosProveedores;
    
    @Autowired
    private PersonasRepository personasRepository;
    
    @Autowired
    private CredencialesRepository credencialesRepository;

    @Autowired
    private ServiciosEjemplares serviciosEjemplares;

    @Autowired
    private ServiciosMensajes serviciosMensajes;

    @Autowired
    private ServiciosPersonas serviciosPersonas;

    @GetMapping("/carrito")
    public String verCarrito(Model model, HttpSession session) {
        List<ItemLoteDTO> lote = (List<ItemLoteDTO>) session.getAttribute("loteCarrito");
        if (lote == null) lote = new ArrayList<>();
        model.addAttribute("lote", lote);
        return "lotes/verLoteCarrito";
    }

    @PostMapping("/añadir")
    public String añadirAlCarrito(@RequestParam String codigoPlanta,
                                  @RequestParam @Min(1) @Max(50) int cantidad,
                                  HttpSession session,
                                  RedirectAttributes redirect) {

        List<ItemLoteDTO> lote = (List<ItemLoteDTO>) session.getAttribute("loteCarrito");
        if (lote == null) lote = new ArrayList<>();

        Plantas planta = serviciosPlantas.buscarPorCodigo(codigoPlanta)
                .orElseThrow(() -> new RuntimeException("Planta no encontrada"));

        Optional<ItemLoteDTO> existente = lote.stream()
                .filter(item -> item.getCodigoPlanta().equals(codigoPlanta))
                .findFirst();

        if (existente.isPresent()) {
            existente.get().setCantidad(existente.get().getCantidad() + cantidad);
        } else {
            lote.add(new ItemLoteDTO(codigoPlanta, planta.getNombreComun(), cantidad));
        }

        session.setAttribute("loteCarrito", lote);
        redirect.addFlashAttribute("exito", "Planta añadida al lote.");
        return "redirect:/lotes/carrito";
    }

    @GetMapping("/eliminar")
    public String eliminarDelCarrito(@RequestParam String codigoPlanta, HttpSession session) {
        List<ItemLoteDTO> lote = (List<ItemLoteDTO>) session.getAttribute("loteCarrito");
        if (lote != null) {
            lote.removeIf(item -> item.getCodigoPlanta().equals(codigoPlanta));
            session.setAttribute("loteCarrito", lote);
        }
        return "redirect:/lotes/carrito";
    }

    @PostMapping("/confirmar")
    public String confirmarLote(@RequestParam Long idProveedor,
                                @RequestParam(defaultValue = "false") boolean urgente,
                                Authentication auth,
                                HttpSession session,
                                RedirectAttributes redirect) {

        List<ItemLoteDTO> loteDTO = (List<ItemLoteDTO>) session.getAttribute("loteCarrito");
        if (loteDTO == null || loteDTO.isEmpty()) {
            redirect.addFlashAttribute("error", "El lote está vacío.");
            return "redirect:/lotes/carrito";
        }

        String username = auth.getName();
        Long idPersona = personasRepository.findByCredencialesUsuario(username)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"))
                .getId();


        var loteGuardado = serviciosLotes.registrarLote(idProveedor, idPersona, urgente);

        List<ComposicionLote> composicion = new ArrayList<>();

        for (ItemLoteDTO item : loteDTO) {
            var planta = serviciosPlantas.buscarPorCodigo(item.getCodigoPlanta())
                    .orElseThrow(() -> new RuntimeException("Planta no encontrada: " + item.getCodigoPlanta()));

            ComposicionLote comp = new ComposicionLote(loteGuardado, planta, item.getCantidad());
            composicion.add(comp);
        }

        serviciosComposicionLote.guardarTodas(composicion);

        session.removeAttribute("loteCarrito");
        redirect.addFlashAttribute("exito", "Lote confirmado y registrado.");
        return "redirect:/lotes/carrito";
    }

    @GetMapping("/gestionLotes")
    public String gestionLotes() {
        return "lotes/gestionLotes";
    }
    
    @GetMapping("/recepcionLotes")
    public String verRecepcionLotes() {
        return "lotes/lotesRecibidos";
    }
    
    @GetMapping("/recibidos")
    public String verLotesRecibidos(Model model) {
        model.addAttribute("recibidos", serviciosLotes.listarLotesRecibidos());
        return "lotes/lotesRecibidos";
    }
    
//    @GetMapping("/pendientes")
//    public String mostrarPendientesRecepcion(Model model) {
//        List<Lote> pendientes = serviciosLotes.listarLotesPendientes();
//        model.addAttribute("pendientes", pendientes);
//        return "lotes/pendientesRecepcion";
//    }
    
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

//    @PostMapping("/recepcionar")
//    public String recepcionarLote(@RequestParam Long idLote,
//                                  RedirectAttributes redirect,
//                                  Model model) {
//        try {
//            serviciosLotes.recepcionarLote(idLote);
//            redirect.addFlashAttribute("exito", "Lote recepcionado correctamente.");
//        } catch (IllegalStateException e) {
//            redirect.addFlashAttribute("error", e.getMessage());
//        }
//        return "redirect:/lotes/pendientes";
//    }
    
//    @PostMapping("/recepcionar")
//    public String recepcionarLote(@RequestParam Long idLote,
//                                  RedirectAttributes redirect,
//                                  Model model,
//                                  Authentication auth) {
//        try {
//            String usuario = auth.getName();
//            Long idPersona = credencialesRepository.findByUsuario(usuario)
//                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
//                    .getPersona().getId();
//
//            serviciosLotes.recepcionarLote(idLote, idPersona);
//            redirect.addFlashAttribute("exito", "Lote recepcionado correctamente.");
//        } catch (IllegalStateException e) {
//            redirect.addFlashAttribute("error", e.getMessage());
//        }
//        return "redirect:/lotes/pendientes";
//    }
    
    @PostMapping("/recepcionar")
    @Transactional
    public String confirmarRecepcion(@RequestParam Long idLote, Authentication auth) {
        Lote lote = serviciosLotes.buscarPorId(idLote)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        String username = auth.getName();
        Personas receptor = serviciosPersonas.buscarPorUsuario(username)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
        lote.setEstado("TERMINADO");
        lote.setFechaHoraRecepcion(LocalDateTime.now());
        lote.setPersonaReceptora(receptor);
        serviciosLotes.guardar(lote);

        List<ComposicionLote> composicion = serviciosComposicionLote.obtenerPorLote(lote);

        for (ComposicionLote comp : composicion) {
        	long existentes = serviciosEjemplares.contarPorPlanta(comp.getPlanta());
        	for (int i = 1; i <= comp.getCantidad(); i++) {
        	    Ejemplares ej = new Ejemplares();
        	    ej.setPlanta(comp.getPlanta());
        	    ej.setNombre(comp.getPlanta().getCodigo() + "_" + (existentes + i));
        	    ej.setLote(lote);
        	    Ejemplares guardado = serviciosEjemplares.guardar(ej);

        	    Mensajes m = new Mensajes();
        	    m.setEjemplar(guardado);
        	    m.setPersona(receptor);
        	    m.setFecha(LocalDateTime.now());
        	    m.setMensaje("Ejemplar " + ej.getNombre() + " recibido el " +
        	            m.getFecha().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
        	            " en el lote " + lote.getId() + " del proveedor " +
        	            lote.getProveedor().getNombre() + " solicitado por " +
        	            lote.getPersonaSolicitante().getNombre() + " y confirmado por " +
        	            receptor.getNombre());

        	    serviciosMensajes.guardar(m);
        	}
        }

        return "redirect:/lotes/pendientes";
    }


}


