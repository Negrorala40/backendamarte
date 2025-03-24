package com.ecommerce.amarte.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "amartedb"; // Clave secreta
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY); // Algoritmo de firma
    private static final String ISSUER = "amarte"; // Quién emite el token

    // 🟢 Genera un token con el email y los roles
    public String create(String email, List<String> roles) {
        return JWT.create()
                .withSubject(email)
                .withIssuer(ISSUER)
                .withClaim("roles", roles) // 🔥 Guardamos los roles como array
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15))) // 15 días
                .sign(ALGORITHM);
    }

    // 🟢 Valida el token
    public boolean isValid(String token) {
        try {
            JWT.require(ALGORITHM)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    // 🟢 Obtiene el email del usuario desde el token
    public String getUsername(String token) {
        return JWT.require(ALGORITHM)
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getSubject();
    }

    // 🟢 Obtiene los roles desde el token
    public List<String> getRoles(String token) {
        return JWT.require(ALGORITHM)
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getClaim("roles") // 🔥 Extraemos los roles como array
                .asList(String.class); // Convertimos en lista
    }
}
