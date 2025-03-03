package com.ecommerce.amarte.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.hibernate.annotations.Collate;

@Getter
@Setter
public class UserDTO {
    
    private Long id; // ID del usuario

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String lastName;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe ser un email válido")
    @Column(nullable = false, unique = true)
    private String email;

    @Size(min = 10, max = 15, message = "El número de teléfono debe tener entre 10 y 15 caracteres")
    @Pattern(regexp = "\\d{10,15}", message = "El teléfono debe contener solo números y tener entre 10 y 15 dígitos")
    private String phone;

    private List<Long> addressIds; // Lista de IDs de direcciones asociadas al usuario (Opcional)
    
    private List<Long> orderIds; // Lista de IDs de pedidos asociados al usuario (Opcional)
    
    private List<Long> cartItemIds; // Lista de IDs de productos en el carrito (Opcional)
}
