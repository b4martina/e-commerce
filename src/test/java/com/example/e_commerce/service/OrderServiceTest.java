package com.example.e_commerce.service;


import com.example.e_commerce.dto.OrderItemRequest;
import com.example.e_commerce.dto.OrderRequest;
import com.example.e_commerce.dto.OrderResponse;
import com.example.e_commerce.exceptions.OrderNotAvailableException;
import com.example.e_commerce.exceptions.ProductNotAvailableException;
import com.example.e_commerce.model.Orders;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.model.User;
import com.example.e_commerce.repository.OrderRepository;
import com.example.e_commerce.repository.ProductRepository;
import com.example.e_commerce.repository.UserRepository;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void OrderService_CreateOrder_ReturnOrder() throws ProductNotAvailableException {
    String username = "username";
    User user= new User();
    user.setUsername(username);

    Product product = new Product();
    product.setStockQuantity(10L);
    product.setPrice(BigDecimal.valueOf(20));

        OrderItemRequest item = new OrderItemRequest();
        item.setProductId(1L);
        item.setQuantity(2);

    OrderRequest orderRequest = new OrderRequest();
    orderRequest.setCustomerName("name");
        orderRequest.setStreetAdress("adress");
        orderRequest.setAdressLine2("abc");
        orderRequest.setPostalCode(1234L);
        orderRequest.setCity("city");
        orderRequest.setCard(true);
        orderRequest.setNameOfCard("name sff");
        orderRequest.setCardNumber(123L);
        orderRequest.setExpirationDate(LocalDate.of(2028, 12, 9));
        orderRequest.setSecurityCode(5678997654323456777L);
        orderRequest.setItems(List.of(item));

        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(orderRepository.save(any(Orders.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        //act
        Orders createOrder = orderService.createOrder(orderRequest, username);

        //assert
    Assertions.assertThat(createOrder).isNotNull();
    Assertions.assertThat(createOrder.getBuyer()).isEqualTo(user);
    Assertions.assertThat(createOrder.getCustomerName()).isEqualTo("name");
    }

        @Test
        public void OrderService_getUserOrders_returnOrders() throws OrderNotAvailableException {
        //arrange
        String username = "username";
        User user= new User();
        user.setUsername(username);
        user.setId(1L);

        when (userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));

        Orders order = new Orders();

        order.setCustomerName("name");
            order.setStreetAdress("adress");
            order.setAdressLine2("abc");
            order.setPostalCode(1234L);
            order.setCity("city");
            order.setCard(true);
            order.setNameOfCard("name sff");
            order.setCardNumber(123L);
            order.setExpirationDate(LocalDate.of(2028, 12, 9));
            order.setSecurityCode(5678997654323456777L);

            Orders order1 = new Orders();

            order1.setCustomerName("name");
            order1.setStreetAdress("adress");
            order1.setAdressLine2("abc");
            order1.setPostalCode(1234L);
            order1.setCity("city");
            order1.setCard(true);
            order1.setNameOfCard("name sff");
            order1.setCardNumber(123L);
            order1.setExpirationDate(LocalDate.of(2028, 12, 9));
            order1.setSecurityCode(5678997654323456777L);
            List<Orders> orders = List.of(order, order1);
            when(orderRepository.findByBuyerId(user.getId())).thenReturn(orders);

            //Act
            List<OrderResponse> result = orderService.getUserOrders(username);
            //Assert
            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).hasSize(2);
            Assertions.assertThat(result.get(0).getCustomerName()).isEqualTo("name");
            Assertions.assertThat(result.get(1).getCustomerName()).isEqualTo("name");

            Assertions.assertThat(result.get(1).getNameOfCard())
                    .isEqualTo("name sff");

            verify(userRepository).findByUsername(username);
            verify(orderRepository).findByBuyerId(user.getId());}


}
