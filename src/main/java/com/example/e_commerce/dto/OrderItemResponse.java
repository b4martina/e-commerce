package com.example.e_commerce.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {
    private Long id;
    private BigDecimal price;
    private int quantity;
    private Long productId;
    private BigDecimal itemTotal;
}
