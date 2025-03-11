package com.ecommerce.amarte.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantDTO {
    
    private Long id;

    @NotBlank(message = "El color es obligatorio")
    private String color;

    @NotBlank(message = "La talla es obligatoria")
    private String size;

    @NotNull(message = "El stock no puede estar vacío")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @NotNull(message = "error productid")
    private Long productId;
}
