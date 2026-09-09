package com.example.e_commerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="ID")
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name= "DESCRIPTION")
    private String description;

    @Column (name = "PRICE")
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @Column(name = " STOCK_QUANTITY")
    @Min(value = 0, message = "Stock cannot be negative")
    private Long stockQuantity;

    @Enumerated(EnumType.STRING)
    @Column (name = "PRODUCT_CATEGORY")
    private Category category;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name= "OWNER_ID")
    @JsonIgnore
    private User productOwner;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
