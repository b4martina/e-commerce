package com.example.e_commerce.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "NAME")
    private String name;

    @Column(name= "DESCRIPTION")
    private String description;

    @Column (name = "PRICE")
    private BigDecimal price;

    @Column(name = " STOCK_QUANTITY")
    private Long stockQuantity;

    @Enumerated(EnumType.STRING)
    @Column (name = "PRODUCT_CATEGORY")
    private Category category;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDate createdAt;

}
