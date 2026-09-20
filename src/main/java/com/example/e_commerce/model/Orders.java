package com.example.e_commerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ORDERS")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    @Column (name= "ORDER_ID")
    private Long orderId;

    @Column(name ="CUSTOMER_NAME")
    private String customerName;

    @Column(name="STREET_ADRESS")
    private String streetAdress;

    @Column(name="ADDRESS_LINE_2")
    private String adressLine2;

    @Column(name="POSTAL_CODE")
    private Long postalCode;

    @Column(name="CITY")
    private String city;

    @Column(name="CARD")
    private boolean card;

    @Column(name="NAME_OF_CARD")
    private String nameOfCard;

    @Column(name="CARD_NUMBER")
    private Long cardNumber;

    @Column(name="EXPIRATION_DATE")
    private LocalDate expirationDate;

    @Column(name="SECURITY_CODE")
    private Long securityCode;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "BUYER_ID")
    private User buyer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    private List <OrderItems > orderItems = new ArrayList<>();

}
