package com.alejandro.tarea7dwesalejandro.servicios;

import org.springframework.security.core.userdetails.*;

public class ServiciosUsuariosCombinados implements UserDetailsService {

    private final UserDetailsService inMemory;
    private final UserDetailsService database;

    public ServiciosUsuariosCombinados(UserDetailsService inMemory, UserDetailsService database) {
        this.inMemory = inMemory;
        this.database = database;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return inMemory.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return database.loadUserByUsername(username);
        }
    }
}
