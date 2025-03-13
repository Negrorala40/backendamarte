package com.ecommerce.amarte.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID de la variante del producto

    private String color; // Color del producto
    private String size; // Talla del producto
    private int stock; // Stock disponible

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // Relación con el producto principal

    // Método para reducir stock al comprar un producto
    public void reduceStock(int quantity) {
        if (this.stock >= quantity) {
            this.stock -= quantity;
        } else {
            throw new IllegalArgumentException("Stock insuficiente para el producto " + this.product.getName());
        }
    }
}
