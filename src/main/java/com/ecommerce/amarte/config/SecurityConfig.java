package com.ecommerce.amarte.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(JwtFilter jwtFilter, CorsConfigurationSource corsConfigurationSource) {
        this.jwtFilter = jwtFilter;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource) // ✅ Integración con CorsConfig
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 🔒 Seguridad mejorada
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/h2-console/**").permitAll()  // ✅ Permitir acceso a H2 Console
                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()  // ✅ Permitir registro sin autenticación
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()  // ✅ Permitir login sin autenticación
                .requestMatchers(HttpMethod.GET, "/api/products").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/cart/add").authenticated()
                .anyRequest().authenticated()  // 🔒 Requiere autenticación para otras rutas
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // ✅ Agregamos el filtro JWT
                .formLogin().disable() // 🔴 Deshabilita autenticación por formulario
                .httpBasic().disable(); // 🔴 Deshabilita autenticación básica

        // ✅ Permitir que H2 funcione en un iframe.
        http.headers().frameOptions().sameOrigin();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
