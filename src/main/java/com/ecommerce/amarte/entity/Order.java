package com.ecommerce.amarte.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID del pedido

    private Date orderDate; // Método de pago
    @Enumerated(EnumType.STRING)
    private OrderStatus status;        // Estado del pedido (Ej. "Pendiente", "Enviado", "Entregado")
    private Double totalPrice;
    
    // private String shippingAddress; // Dirección de envío

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Relación con el usuario

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems; // Productos del pedido

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address shipingAddress;
}