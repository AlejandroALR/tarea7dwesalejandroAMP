package com.alejandro.tarea7dwesalejandro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosDetallesUsuarios;
import com.alejandro.tarea7dwesalejandro.servicios.ServiciosUsuariosCombinados;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Autowired
    private CustomSuccessHandler customSuccessHandler;

    @Autowired
    private LogoutCarritoHandler logoutCarritoHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService combinedUserDetailsService(ServiciosDetallesUsuarios serviciosDetallesUsuarios) {
        UserDetails admin = User.builder()
                .username(adminUsername)
                .password(adminPassword)
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .build();

        InMemoryUserDetailsManager memory = new InMemoryUserDetailsManager(admin);
        return new ServiciosUsuariosCombinados(memory, serviciosDetallesUsuarios);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService combinedUserDetailsService,
                                                            BCryptPasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(combinedUserDetailsService);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Acceso libre (todos los roles incluyendo invitado)
                .requestMatchers("/", "/login", "/logout", "/clientes/registro", "/plantas/verPlantasInvi", "/error/accesoDenegado", "/invitado/**", "/css/**", "/js/**", "/images/**").permitAll()

                // Zona clientes (CLIENTE, ADMIN y PERSONAL)
                .requestMatchers("/pedidos/filtrar").hasAnyRole("PERSONAL")
                .requestMatchers("/clientes/**", "/pedidos/**").hasAnyRole("CLIENTE")

                // Zona ejemplares (ADMIN, PERSONAL)
                .requestMatchers("/ejemplares/**").hasAnyRole("ADMIN", "PERSONAL")

                // Zona mensajes (ADMIN, PERSONAL)
                .requestMatchers("/mensajes/**").hasAnyRole("ADMIN", "PERSONAL")

                // Zona proveedores (ADMIN, PROVEEDORES)
                .requestMatchers("/proveedor/**").hasAnyRole("PROVEEDOR", "ADMIN")
                
                // Zona perfiles
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/cliente").hasAnyRole("CLIENTE")
                .requestMatchers("/personal").hasAnyRole("PERSONAL", "ADMIN")
                .requestMatchers("/proveedor").hasRole("PROVEEDOR")
                .requestMatchers("/invitado").permitAll()

                // Zona personas (ADMIN)
                .requestMatchers("/personas/**").hasRole("ADMIN")

                // Zona plantas
                .requestMatchers("/plantas/gestionPlantas", "/plantas/modificarPlanta", "/plantas/registrarPlanta", "/proveedores/registrarProveedor").hasRole("ADMIN")
                .requestMatchers("/plantas/verPlantas").hasAnyRole("ADMIN", "PERSONAL")

                // Todo lo demás requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(customSuccessHandler)
                .permitAll()
            )
            .logout(logout -> logout
            	.logoutUrl("/logout")
                .logoutSuccessHandler(logoutCarritoHandler)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/error/accesoDenegado")
            )
            .sessionManagement(session -> session
                .invalidSessionUrl("/login")
                .sessionFixation(sessionFixation -> sessionFixation.migrateSession())
            );

        return http.build();
    }

}
