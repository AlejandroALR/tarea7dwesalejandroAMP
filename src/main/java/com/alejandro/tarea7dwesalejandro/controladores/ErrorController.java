package com.alejandro.tarea7dwesalejandro.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error/accesoDenegado")
    public String accesoDenegado() {
        return "error/accesoDenegado";
    }
}
