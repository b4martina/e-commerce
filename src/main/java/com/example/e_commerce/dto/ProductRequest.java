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
public class ProductRequest {
    @NotBlank(message= "Name can not be blank ")
    private  String name;

    @NotBlank(message= "Description can not be blank ")
    private String description;

    @NotBlank(message= "Price can not be blank ")
    private BigDecimal price;


    @NotBlank(message= "Stock Quantity can not be blank ")

    private Long stockQuantity;

    @NotBlank(message= "Category can not be blank ")
    private Category category;


}
