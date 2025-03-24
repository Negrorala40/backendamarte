package com.ecommerce.amarte.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) // Almacena el enum como texto en la BD
    @Column(nullable = false, unique = true)
    private UserRoleEnum role;

    public UserRole(UserRoleEnum role) {
        this.role = role;
    }
}
