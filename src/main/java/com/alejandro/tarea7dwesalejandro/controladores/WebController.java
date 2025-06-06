package com.alejandro.tarea7dwesalejandro.controladores;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index"; // index.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // login.html
    }

    @GetMapping("/home")
    public String home(Authentication auth, Model model) {
        if (auth == null) {
            return "redirect:/login";
        }

        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "redirect:/admin";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PERSONAL"))) {
            return "redirect:/personal";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CLIENTE"))) {
            return "redirect:/cliente";
        }else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PROVEEDOR"))) {
            return "redirect:/proveedor";
        }

        return "redirect:/invitado";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "perfiles/admin"; // admin.html
    }

    @GetMapping("/personal")
    @PreAuthorize("hasRole('PERSONAL', 'ADMIN')")
    public String personal() {
        return "perfiles/personal"; // personal.html
    }

    @GetMapping("/cliente")
    @PreAuthorize("hasRole('CLIENTE', 'ADMIN', 'PERSONAL')")
    public String cliente() {
        return "perfiles/cliente"; // cliente.html 
    }

    @GetMapping("/invitado")
    public String invitado() {
        return "perfiles/invitado"; // invitado.html
    }
    
    @GetMapping("/proveedor")
    @PreAuthorize("hasRole('PROVEEDOR')")
    public String menuProveedor() {
        return "perfiles/proveedor";
    }

    
    
}

