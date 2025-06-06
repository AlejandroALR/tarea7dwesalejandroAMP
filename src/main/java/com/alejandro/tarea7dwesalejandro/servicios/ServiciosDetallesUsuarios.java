package com.alejandro.tarea7dwesalejandro.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.alejandro.tarea7dwesalejandro.modelo.Credenciales;
import com.alejandro.tarea7dwesalejandro.repositorios.CredencialesRepository;

import java.util.Collections;

@Service
public class ServiciosDetallesUsuarios implements UserDetailsService {

    @Autowired
    private CredencialesRepository credencialesRepository;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Credenciales credenciales = credencialesRepository.findByUsuario(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String rol;
        if (credenciales.getPersona() != null) {
            rol = credenciales.getPersona().getRol();
        } else if (credenciales.getProveedor() != null) {
            rol = "PROVEEDOR";
        } else {
            throw new UsernameNotFoundException("Credenciales sin entidad asociada");
        }

        return User.builder()
                .username(credenciales.getUsuario())
                .password(credenciales.getPassword())
                .roles(rol)
                .build();
    }

}
