package com.example.e_commerce.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockRequest {
@NotNull(message = "Stock adjustment can not be null")
    private Long stockQuantity;

}
