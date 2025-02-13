package com.ecommerce.amarte.entity;

import lombok.Data;
import lombok.NoArgsConstructor;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orderItems")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID del producto en el pedido

    private Integer quantity;  // Cantidad del producto en el pedido
    private Double price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;   // Relación con el pedido

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductVariant productVariant;
}
