package com.example.e_commerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "ORDER")
public class Order{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    @Column (name= "ORDER_ID")
    private Long orderId;


   /* @Column(name = "PRODUCT_ID")
    private Long productId;


    @Column(name = "USER_ID")
    private Long user_id;*/

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
    @JoinColumn(name = "ORDERS_OF_USERS")
    private User buyer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    private List <OrderItems> orderItem = new ArrayList<>();


}
