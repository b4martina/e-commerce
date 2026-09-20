package com.example.e_commerce.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

//@Builder
@Data

public class OrderResponse {

    private String customerName;
    private String streetAdress;

    private String city;

    private boolean card;

    private String nameOfCard;


}
