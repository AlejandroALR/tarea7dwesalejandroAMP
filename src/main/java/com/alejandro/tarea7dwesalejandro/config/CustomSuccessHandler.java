package com.alejandro.tarea7dwesalejandro.config;

import com.alejandro.tarea7dwesalejandro.modelo.Credenciales;
import com.alejandro.tarea7dwesalejandro.modelo.Personas;
import com.alejandro.tarea7dwesalejandro.modelo.Proveedor;
import com.alejandro.tarea7dwesalejandro.repositorios.CredencialesRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private CredencialesRepository credencialesRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();
        Credenciales cred = credencialesRepository.findByUsuario(username).orElse(null);

        if (cred != null) {
            HttpSession session = request.getSession();

            if (cred.getPersona() != null) {
                session.setAttribute("persona", cred.getPersona());
            } else if (cred.getProveedor() != null) {
                session.setAttribute("proveedor", cred.getProveedor());
            }

            session.setAttribute("credenciales", cred);

            for (GrantedAuthority auth : authentication.getAuthorities()) {
                String rol = auth.getAuthority();
                switch (rol) {
                    case "ROLE_ADMIN":
                        response.sendRedirect("/admin");
                        return;
                    case "ROLE_PERSONAL":
                        response.sendRedirect("/personal");
                        return;
                    case "ROLE_CLIENTE":
                        response.sendRedirect("/cliente");
                        return;
                    case "ROLE_PROVEEDOR":
                        response.sendRedirect("/proveedor");
                        return;
                }
            }
        }

        response.sendRedirect("/invitado");
    }
}

