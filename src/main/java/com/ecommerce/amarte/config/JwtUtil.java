package com.ecommerce.amarte.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "amartedb"; // Clave secreta para el JWT
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY); // Algoritmo de firma

    // Método para generar un token JWT
    public String create(String email) {
        return JWT.create()
                .withSubject(email)
                .withIssuer("amarte")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15))) // 15 días de validez
                .sign(ALGORITHM); // Firmamos el token
    }

    // Método para validar si un token es correcto
    public boolean isValid(String token) {
        try {
            JWT.require(ALGORITHM)
                    .build()
                    .verify(token); // Corregido el uso de `token`
            return true;
        } catch (JWTVerificationException e) { // Agregado `e` en la excepción
            return false;
        }
    }

    // Método para obtener el email del usuario desde el token
    public String getUsername(String token) {
        return JWT.require(ALGORITHM)
                .build()
                .verify(token) // Corregido el uso de `token`
                .getSubject();
    }
}
