package com.ecommerce.amarte.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {

    private Long id; // ID del pedido

    @NotNull(message = "La fecha del pedido no puede estar vacía")
    @PastOrPresent(message = "La fecha del pedido no puede ser futura")
    private Date orderDate; // Fecha del pedido

    @NotNull(message = "El estado del pedido es obligatorio")
    private String status; // Estado del pedido (Ej. "Pendiente", "Enviado", "Entregado")

    @NotNull(message = "El precio total no puede estar vacío")
    @PositiveOrZero(message = "El precio total no puede ser negativo")
    private Double totalPrice; // Precio total del pedido

    @NotNull(message = "El usuario asociado al pedido es obligatorio")
    private Long userId; // ID del usuario que realizó el pedido

    @NotEmpty(message = "El pedido debe contener al menos un producto")
    private List<OrderItemDTO> orderItems; // Lista de productos en el pedido

    @NotNull(message = "La dirección de envío es obligatoria")
    private Long shippingAddressId; // ID de la dirección de envío
}
