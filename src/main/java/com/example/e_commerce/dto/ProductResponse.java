package com.example.e_commerce.dto;

import com.example.e_commerce.model.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

        private  String name;
        private String description;
        private BigDecimal price;
        private Long stockQuantity;
        private Category category;


    }



