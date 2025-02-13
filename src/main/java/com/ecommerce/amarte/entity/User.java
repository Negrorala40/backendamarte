package com.ecommerce.amarte.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "custumers")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID del usuario
    
    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String email;
    private String phone;
    @Column(nullable = false)
    private String password; // Contraseña encriptada
    
    // private boolean isGoogleAuthEnabled;
    
    // private String googleAuthenticatorSecret; // Para autenticación con Google

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Addres> addresses;

    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders; // Lista de pedidos realizados por el usuario

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems; // Productos en el carrito
}
