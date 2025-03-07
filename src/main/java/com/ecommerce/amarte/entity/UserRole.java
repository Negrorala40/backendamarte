package com.ecommerce.amarte.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")  // Nombre de la tabla
@Data
@NoArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String role;  // Ej: "ADMIN", "CUSTOMER"

    // Constructor adicional
    public UserRole(String role) {
        this.role = role;
    }


}
