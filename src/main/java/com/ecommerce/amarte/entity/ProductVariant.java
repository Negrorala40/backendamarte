package com.ecommerce.amarte.entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID de la variante del producto

    private String color; // Color del producto
    private String size;  // Talla del producto
    private int stock;    // Stock disponible
    private BigDecimal price; // Precio específico de esta variante

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore // <- evita serializar esto si ya se incluye en otro lugar
    private Product product; // Relación con el producto principal

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Img> images;


    // Método para reducir stock al comprar un producto
    public void reduceStock(int quantity) {
        if (this.stock >= quantity) {
            this.stock -= quantity;
        } else {
            throw new IllegalArgumentException("Stock insuficiente para el producto " + this.product.getName());
        }
    }
}
