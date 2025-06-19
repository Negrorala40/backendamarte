package com.ecommerce.amarte.dto;

import com.ecommerce.amarte.entity.ProductVariant;
import com.ecommerce.amarte.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

    private Long id; // ID del ítem en el carrito
    private int quantity; // Cantidad del producto en el carrito
    private User user; // Usuario que agregó el ítem al carrito
    private ProductVariant productVariant; // Variante del producto (ej. color, tamaño, etc.)
    private BigDecimal totalPrice; // Precio total calculado para este ítem


}
