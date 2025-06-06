package com.alejandro.tarea7dwesalejandro.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alejandro.tarea7dwesalejandro.modelo.Credenciales;
import com.alejandro.tarea7dwesalejandro.repositorios.CredencialesRepository;

@Service
public class ServiciosCredenciales {

    @Autowired
    private CredencialesRepository credencialesRepository;

    public Credenciales insertarCredenciales(Credenciales c) {
        return credencialesRepository.save(c);
    }

    public boolean usuarioExiste(String usuario) {
        return credencialesRepository.existsByUsuario(usuario);
    }

    public Optional<Credenciales> findByUsu(String usuario) {
        return credencialesRepository.findByUsuario(usuario);
    }

    public Optional<Credenciales> findById(Long id) {
        return credencialesRepository.findById(id);
    }

    public List<Credenciales> findAll() {
        return credencialesRepository.findAll();
    }
}
