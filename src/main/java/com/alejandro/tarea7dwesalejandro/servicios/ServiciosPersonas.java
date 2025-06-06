package com.alejandro.tarea7dwesalejandro.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alejandro.tarea7dwesalejandro.dto.RegistroPersonaDTO;
import com.alejandro.tarea7dwesalejandro.modelo.Credenciales;
import com.alejandro.tarea7dwesalejandro.modelo.Personas;
import com.alejandro.tarea7dwesalejandro.repositorios.CredencialesRepository;
import com.alejandro.tarea7dwesalejandro.repositorios.PersonasRepository;

@Service
public class ServiciosPersonas {

    @Autowired
    private CredencialesRepository credencialesRepository;

    @Autowired
    private PersonasRepository personasRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public void registrarPersonaConCredenciales(RegistroPersonaDTO dto) {
        Personas persona = new Personas();
        persona.setNombre(dto.getNombre());
        persona.setEmail(dto.getEmail());
        persona.setRol(dto.getRol());

        persona = personasRepository.save(persona);

        Credenciales credencial = new Credenciales();
        credencial.setUsuario(dto.getUsuario());
        credencial.setPassword(passwordEncoder.encode(dto.getPassword()));
        credencial.setPersona(persona);

        persona.setCredenciales(credencial);

        credencialesRepository.save(credencial);
    }

    public Personas guardar(Personas persona) {
        return personasRepository.save(persona);
    }

    public Optional<Personas> buscarPorId(Long id) {
        return personasRepository.findById(id);
    }

    public Optional<Personas> buscarPorEmail(String email) {
        return personasRepository.findByEmail(email);
    }

    public List<Personas> listarTodas() {
        return personasRepository.findAll();
    }

    public boolean emailExiste(String email) {
        return personasRepository.existsByEmail(email);
    }

    public boolean usuarioExiste(String usuario) {
        return credencialesRepository.findByUsuario(usuario).isPresent();
    }

    public void actualizarCredenciales(Personas persona, Long idCredenciales) {
        personasRepository.save(persona);
    }

    public Long obtenerIdDesdeAuth(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }

        String username = auth.getName();

        return personasRepository.findByUsuario(username)
                .map(Personas::getId)
                .orElseThrow(() -> new RuntimeException("No se encontró la persona para el usuario autenticado"));
    }

    public Personas obtenerPersonaAutenticada() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }

        String username = auth.getName();
        return credencialesRepository.findByUsuario(username)
                .map(Credenciales::getPersona)
                .orElseThrow(() -> new RuntimeException("No se encontró la persona autenticada"));
    }
    
    public Optional<Personas> buscarPorUsuario(String usuario) {
        return personasRepository.findByCredencialesUsuario(usuario);
    }

    
    
}

