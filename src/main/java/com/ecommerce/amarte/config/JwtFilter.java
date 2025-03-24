package com.ecommerce.amarte.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            // Validar que el encabezado no sea nulo, vacío y tenga el prefijo "Bearer "
            if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            // Extraer el token eliminando el prefijo "Bearer "
            String jwt = authHeader.substring(7).trim();

            // Validar el token
            if (!jwtUtil.isValid(jwt)) {
                filterChain.doFilter(request, response);
                return;
            }

            // Si ya hay una autenticación en el contexto, no hacer nada
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                // Obtener el usuario y roles desde el token
                String username = jwtUtil.getUsername(jwt);
                List<String> roles = jwtUtil.getRoles(jwt);

                // Convertir roles en GrantedAuthority
                List<GrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Prefijo ROLE_
                        .collect(Collectors.toList());

                // Crear la autenticación y establecerla en el contexto de seguridad
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, authorities
                );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            // Evitar que una excepción bloquee la solicitud
            System.err.println("🔴 Error en JwtFilter: " + e.getMessage());
            filterChain.doFilter(request, response);
        }
    }
}
