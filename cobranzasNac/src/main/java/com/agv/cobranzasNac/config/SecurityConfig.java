package com.agv.cobranzasNac.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. DESHABILITAR CSRF
            // (Esto es necesario para que tus formularios POST de Thymeleaf funcionen)
            .csrf(csrf -> csrf.disable())
            
            // 2. AUTORIZAR PETICIONES
            .authorizeHttpRequests(auth -> auth
                // 3. PERMITIR TODO
                // (Le dice a Spring Security que no pida login para NINGUNA ruta)
                .anyRequest().permitAll() 
            );
        
        return http.build();
    }
}