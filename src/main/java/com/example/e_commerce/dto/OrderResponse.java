package com.example.e_commerce.dto;

import com.example.e_commerce.model.OrderItems;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//@Builder
@Data

public class OrderResponse {
    private String orderDescription;
    private String customerName;
    private String streetAdress;
    private String city;
    private boolean card;
    private String nameOfCard;
    private BigDecimal orderPrice;
    private List <OrderItemResponse> orderItems;
}
