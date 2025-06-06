package com.alejandro.tarea7dwesalejandro.servicios;

import com.alejandro.tarea7dwesalejandro.dto.RegistroProveedorDTO;
import com.alejandro.tarea7dwesalejandro.modelo.Credenciales;
import com.alejandro.tarea7dwesalejandro.modelo.Proveedor;
import com.alejandro.tarea7dwesalejandro.repositorios.CredencialesRepository;
import com.alejandro.tarea7dwesalejandro.repositorios.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiciosProveedores {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private CredencialesRepository credencialesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Proveedor> listarTodos() {
        return proveedorRepository.findAll();
    }

    @Transactional
    public void registrarProveedorConCredenciales(RegistroProveedorDTO dto) {
        if (proveedorRepository.existsByCif(dto.getCif())) {
            throw new IllegalArgumentException("Ya existe un proveedor con ese CIF");
        }
        if (credencialesRepository.findByUsuario(dto.getUsuario()).isPresent()) {
            throw new IllegalArgumentException("Ese nombre de usuario ya está en uso");
        }

        // Crear proveedor
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(dto.getNombre());
        proveedor.setCif(dto.getCif());
        proveedor.setRol("PROVEEDOR");
      


        // Crear credenciales
        Credenciales cred = new Credenciales();
        cred.setUsuario(dto.getUsuario());
        cred.setPassword(passwordEncoder.encode(dto.getPassword()));
        cred.setProveedor(proveedor);
        

        credencialesRepository.save(cred);  
        proveedor.setCredenciales(cred);
        proveedorRepository.save(proveedor);
    }

    public boolean cifYaExiste(String cif) {
        return proveedorRepository.existsByCif(cif);
    }

    public boolean usuarioYaExiste(String nombreUsuario) {
        return credencialesRepository.findByUsuario(nombreUsuario).isPresent();
    }
}
