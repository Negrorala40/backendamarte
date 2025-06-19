package com.ecommerce.amarte.controller;

import org.springframework.web.bind.annotation.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/bold")
public class BoldPaymentController {

    private static final String SECRET_KEY = "l2UZtdbfSpPy-r29FOODcA"; // Llave secreta de pruebas

    @PostMapping("/signature")
    public Map<String, String> generarFirma(@RequestBody Map<String, Object> body) {
        String orderId = body.get("orderId").toString();
        String currency = body.get("currency").toString();
        String amount = body.get("amount").toString();

        try {
            String datos = String.format("order-id=%s&currency=%s&amount=%s", orderId, currency, amount);

            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256");
            hmacSha256.init(secretKey);

            byte[] hash = hmacSha256.doFinal(datos.getBytes());
            String signature = Base64.getEncoder().encodeToString(hash);

            Map<String, String> response = new HashMap<>();
            response.put("signature", signature);
            return response;

        } catch (Exception e) {
            throw new RuntimeException("Error al generar la firma", e);
        }
    }
}
