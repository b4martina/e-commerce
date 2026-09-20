package com.example.e_commerce.repository;


import com.example.e_commerce.model.Orders;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.TestComponent;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Test
    public void OrderRepository_SaveALl_ReturnSavedOrderRepository(){

        //Arrange
        Orders orders = Orders.builder().customerName("name")
                .streetAdress("adress")
                .adressLine2("abc")
                .postalCode(1234L)
                .city("city")
                .card(true)
                .nameOfCard("name sff")
                .cardNumber(123L)
                .expirationDate(LocalDate.of(2028, 12, 9))
                .securityCode(5678997654323456777L)
                .build();

        //Act
        Orders savedOrders=orderRepository.save(orders);
        //Assert

        Assertions.assertThat(savedOrders).isNotNull();
        Assertions.assertThat(savedOrders.getOrderId()).isGreaterThan(0);}

    @Test
    public void OrderRepository_GetAll_ReturnAllOrders(){

        //Arrange
        Orders orders = Orders.builder().customerName("name")
                .streetAdress("adress")
                .adressLine2("abc")
                .postalCode(1234L)
                .city("city")
                .card(true)
                .nameOfCard("name sff")
                .cardNumber(123L)
                .expirationDate(LocalDate.of(2028, 12, 9))
                .securityCode(5678997654323456777L)
                .build();

        Orders orders2 = Orders.builder().customerName("twname")
                .streetAdress("2adress")
                .adressLine2("tabc")
                .postalCode(1234L)
                .city("cityl")
                .card(true)
                .nameOfCard("qname sff")
                .cardNumber(123L)
                .expirationDate(LocalDate.of(2029, 12, 9))
                .securityCode(567899765432356777L)
                .build();


        orderRepository.save(orders);
        orderRepository.save(orders2);

        //Act
        List<Orders> orderList= orderRepository.findAll();
        //Assert

        Assertions.assertThat(orderList).isNotNull();
        Assertions.assertThat(orderList.size()).isEqualTo(2);

    }

    @Test
    public void OrderRepository_findById_getOrders (){

        //arrange
        Orders orders = Orders.builder().customerName("name")
                .streetAdress("adress")
                .adressLine2("abc")
                .postalCode(1234L)
                .city("city")
                .card(true)
                .nameOfCard("name sff")
                .cardNumber(123L)
                .expirationDate(LocalDate.of(2028, 12, 9))
                .securityCode(5678997654323456777L)
                .build();

        //act
        orderRepository.save(orders);

        Orders orderReturn = orderRepository.findById(orders.getOrderId()).get();
        //assert
        Assertions.assertThat(orderReturn).isNotNull();
    }





}
