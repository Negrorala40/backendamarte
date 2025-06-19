package com.ecommerce.amarte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDTO {

    private Long id;
    private int quantity;
    private BigDecimal totalPrice;

    private Long productVariantId;
    private String color;
    private String size;
    private int stock;
    private BigDecimal price;

    private Long productId;
    private String productName;
    private List<String> imageUrls;
}
