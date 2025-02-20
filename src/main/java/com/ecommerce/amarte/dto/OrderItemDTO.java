package com.ecommerce.amarte.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private Long id; // ID del producto en el pedido

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a 0")
    private Integer quantity; // Cantidad del producto en el pedido

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double price; // Precio unitario del producto en el pedido

    @NotNull(message = "El ID del pedido es obligatorio")
    private Long orderId; // Referencia al ID del pedido

    @NotNull(message = "El ID de la variante del producto es obligatorio")
    private Long productVariantId; // Referencia al ID de la variante del producto
}