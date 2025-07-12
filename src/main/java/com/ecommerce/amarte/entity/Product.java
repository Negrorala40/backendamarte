package com.ecommerce.amarte.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID del producto

    private String name;
    private String description; // Descripción del producto

    @Enumerated(EnumType.STRING)
    private ProductGender gender;

    @Enumerated(EnumType.STRING)
    private ProductType type; // Tipo (Ej. "Superior", "Inferior", "Calzado")

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants; // Lista de variantes

}
