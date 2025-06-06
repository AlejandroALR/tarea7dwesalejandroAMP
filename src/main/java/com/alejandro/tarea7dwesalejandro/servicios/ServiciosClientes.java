package com.alejandro.tarea7dwesalejandro.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alejandro.tarea7dwesalejandro.dto.RegistroClienteDTO;
import com.alejandro.tarea7dwesalejandro.modelo.Clientes;
import com.alejandro.tarea7dwesalejandro.modelo.Credenciales;
import com.alejandro.tarea7dwesalejandro.repositorios.ClienteRepository;
import com.alejandro.tarea7dwesalejandro.repositorios.CredencialesRepository;

@Service
public class ServiciosClientes {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CredencialesRepository credencialesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registrarCliente(RegistroClienteDTO dto) {
        Clientes cliente = new Clientes();
        cliente.setNombre(dto.getNombreCompleto());
        cliente.setEmail(dto.getEmail());
        cliente.setFechaNacimiento(dto.getFechaNacimiento());
        cliente.setNifNie(dto.getNifNie());
        cliente.setDireccionEnvio(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());

        Credenciales cred = new Credenciales();
        cred.setUsuario(dto.getUsuario());
        cred.setPassword(passwordEncoder.encode(dto.getPassword()));
        cred.setPersona(cliente);

        cliente.setCredenciales(cred);

        clienteRepository.save(cliente);
    }

    public boolean emailYaExiste(String email) {
        return clienteRepository.existsByEmail(email);
    }
    
    public boolean usuarioYaExiste(String usuario) {
        return credencialesRepository.existsByUsuario(usuario);
    }

    public boolean nifYaExiste(String nifNie) {
        return clienteRepository.existsByNifNie(nifNie);
    }

}
