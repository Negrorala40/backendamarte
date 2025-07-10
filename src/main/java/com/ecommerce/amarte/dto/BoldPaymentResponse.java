package com.ecommerce.amarte.dto;

import lombok.Data;

@Data
public class BoldPaymentResponse {
    private String orderId;
    private Integer amount;
    private String signature;
}

