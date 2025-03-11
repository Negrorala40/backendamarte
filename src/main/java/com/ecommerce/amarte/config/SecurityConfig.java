package com.ecommerce.amarte.config;

import com.ecommerce.amarte.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:3000"));  // Permitir todas las rutas de localhost:3000
                    config.setAllowedMethods(List.of("*"));  // Permitir todos los métodos (GET, POST, etc.)
                    config.setAllowedHeaders(List.of("*"));  // Permitir todos los headers
                    config.setAllowCredentials(true);  // Permitir credenciales (cookies, etc.)
                    return config;
                })
                .and()
                .authorizeHttpRequests()
                .anyRequest().permitAll()  // Permitir todas las solicitudes sin autenticación
                .and()
                .httpBasic();  // Mantener autenticación básica si se necesita

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }
}
