package com.example.e_commerce.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private String orderDescription;
    private String customerName;


    @NotBlank
    private String streetAdress;


    @NotBlank
    private String adressLine2;

    @Min(1)
    private Long postalCode;

    @NotBlank
    private String city;

    private boolean card;

    @NotBlank
    private String nameOfCard;

    private Long cardNumber;

    private LocalDate expirationDate;

    private Long securityCode;

    @Valid
    private List<OrderItemRequest> items;
}


