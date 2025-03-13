package com.ecommerce.amarte.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = System.getenv("JWT_SECRET_KEY") != null
            ? System.getenv("JWT_SECRET_KEY")
            : "clave_por_defecto_muy_larga_y_segura";

    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);
    private final JWTVerifier verifier;

    public JwtUtil() {
        this.verifier = JWT.require(ALGORITHM).withIssuer("amarte").build();
    }

    // Método para generar un token JWT
    public String create(String email) {
        return JWT.create()
                .withSubject(email)
                .withIssuer("amarte")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15))) // 15 días de validez
                .sign(ALGORITHM);
    }

    // Método para validar si un token es correcto
    public boolean isValid(String token) {
        try {
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    // Método para obtener el email del usuario desde el token
    public String getUsername(String token) {
        try {
            return verifier.verify(token).getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }
}
