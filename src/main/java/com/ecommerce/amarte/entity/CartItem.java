package com.ecommerce.amarte.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID del ítem en el carrito

    private int quantity; // Cantidad del producto en el carrito

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Relación con el usuario que agregó el producto

    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    private ProductVariant productVariant; // Relación con la variante del producto

    // Método corregido para calcular el precio total basado en la variante
    public BigDecimal getTotalPrice() {
        return this.productVariant.getPrice().multiply(BigDecimal.valueOf(this.quantity));
    }
}
