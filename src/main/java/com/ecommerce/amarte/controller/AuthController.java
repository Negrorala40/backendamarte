package com.ecommerce.amarte.controller;

import com.ecommerce.amarte.config.JwtUtil;
import com.ecommerce.amarte.dto.AuthResponse;
import com.ecommerce.amarte.dto.LoginDTO;
import com.ecommerce.amarte.entity.User;
import com.ecommerce.amarte.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 🔥 Buscar usuario en la base de datos
            Optional<User> optionalUser = userService.getUserByEmail(loginDTO.getEmail());

            if (!optionalUser.isPresent()) {
                return ResponseEntity.status(404).body("❌ Usuario no encontrado");
            }

            User user = optionalUser.get();

            // 🔥 Obtener roles del usuario
            List<String> roles = user.getRoles().stream()
                    .map(role -> role.getRole().name()) // Convertir Enum a String
                    .collect(Collectors.toList());

            // 🔥 Generar JWT con email y roles
            String jwt = jwtUtil.create(user.getEmail(), user.getId(), roles);

            // ✅ Respuesta JSON con datos del usuario
            AuthResponse authResponse = new AuthResponse(user.getId(), roles, jwt);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                    .body(authResponse);

        } catch (Exception e) {
            return ResponseEntity.status(403).body("❌ Credenciales incorrectas");
        }
    }
}
