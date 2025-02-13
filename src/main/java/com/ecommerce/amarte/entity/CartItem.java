package com.ecommerce.amarte.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID del carrito
    
    private int quantity; // Cantidad del producto en el carrito

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Relación con el usuario

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductVariant productVariant; // Relación con el producto vari
}
