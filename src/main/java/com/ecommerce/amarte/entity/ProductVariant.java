package com.ecommerce.amarte.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;
    private String size;

    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
