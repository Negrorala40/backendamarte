package com.ecommerce.amarte.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO {

    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100, message = "El nombre del producto no puede tener más de 100 caracteres")
    private String name;

    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String description;

    @NotNull(message = "El género del producto es obligatorio")
    @Size(max = 50, message = "El género no puede superar los 50 caracteres")
    private String gender;

    @NotNull(message = "El tipo de producto es obligatorio")
    @Size(max = 50, message = "El tipo de producto no puede superar los 50 caracteres")
    private String type;

    @NotNull(message = "Las variantes del producto son obligatorias")
    private List<@NotNull ProductVariantDTO> variants;

}
