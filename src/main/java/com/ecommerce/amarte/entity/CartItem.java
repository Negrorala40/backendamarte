package com.ecommerce.amarte.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    // Método para obtener el precio total de este producto en el carrito
    public double getTotalPrice() {
        return this.productVariant.getProduct().getPrice() * this.quantity;
    }
}
