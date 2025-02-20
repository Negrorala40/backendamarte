package com.ecommerce.amarte.entity;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID del producto
    
    private String name;
    private String description; // Descripción del producto
    @Enumerated(EnumType.STRING)
    private ProductGender gender;

    @Enumerated(EnumType.STRING)
    private productType type;       // Tipo (Ej. "Superior", "Inferior", "Calzado")
    private double price;      // Precio
    // private String imageUrl;   // URL de la imagen

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)  // Relación inversa: el campo 'product' en Img
    private List<Img> images;
}
