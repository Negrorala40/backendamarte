package com.ecommerce.amarte.controller;

import com.ecommerce.amarte.config.JwtUtil;
import com.ecommerce.amarte.dto.BoldPaymentResponse;
import com.ecommerce.amarte.entity.CartItem;
import com.ecommerce.amarte.service.CartItemService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bold")
public class BoldPaymentController {

    private static final String SECRET_KEY = "l2UZtdbfSpPy-r29FOODcA"; // llave secreta
    private static final String CURRENCY = "COP";

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CartItemService cartItemService;

    @PostMapping("/signature")
    public BoldPaymentResponse generarFirma(HttpServletRequest request) {
        String token = extractToken(request);

        // Revisar si el token llegó vacío o incorrecto
        System.out.println("🔑 Token recibido: " + token);

        if (token == null || token.isEmpty()) {
            System.out.println("❌ El token está vacío o nulo");
            throw new RuntimeException("Token no recibido o vacío");
        }

        Long userId = jwtUtil.getUserId(token);

        // Verificar el userId extraído
        System.out.println("🧑 userId extraído: " + userId);

        List<CartItem> items = cartItemService.getCartItemsByUserId(userId);
        BigDecimal total = items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int amount = total.intValue(); // sin decimales
        String orderId = "ORD-" + UUID.randomUUID();

        String cadena = String.format("order-id=%s&currency=%s&amount=%s", orderId, CURRENCY, amount);
        String signature = createHmac256(cadena, SECRET_KEY);

        System.out.println("Cadena para firmar: " + cadena);
        System.out.println("Firma generada: " + signature);

        BoldPaymentResponse response = new BoldPaymentResponse();
        response.setAmount(amount);
        response.setOrderId(orderId);
        response.setSignature(signature);
        return response;
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return authHeader != null && authHeader.startsWith("Bearer ")
                ? authHeader.substring(7)
                : null;
    }

    private String createHmac256(String data, String secret) {
        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            hmacSha256.init(keySpec);
            byte[] hash = hmacSha256.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error generando firma", e);
        }
    }
}
