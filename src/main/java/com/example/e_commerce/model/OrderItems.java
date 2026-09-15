package com.example.e_commerce.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="ORDER_ITEM")
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="ORDER_ITEM_ID")
    private Long id;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "PRICE ")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name=" ORDER_ID")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}

