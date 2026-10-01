package com.example.e_commerce.controller;

import com.example.e_commerce.dto.OrderItemResponse;
import com.example.e_commerce.dto.OrderResponse;
import com.example.e_commerce.exceptions.OrderNotAvailableException;
import com.example.e_commerce.exceptions.ProductNotAvailableException;
import com.example.e_commerce.service.CustomUserDetailService;
import com.example.e_commerce.service.JWTService;
import com.example.e_commerce.service.OrderService;
//import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private OrderService orderService;
    @MockitoBean
    private JWTService jwtService;
    @MockitoBean
    private CustomUserDetailService customUserDetailService;


    private String username;
    private OrderResponse order;
    private OrderResponse order1;
    @BeforeEach
    void setUp() {
        username = "username";

        OrderItemResponse item1 = new OrderItemResponse();
        item1.setId(1L);
        item1.setPrice(new BigDecimal("12.0"));
        item1.setProductId(5L);
        item1.setQuantity(8);

        OrderItemResponse item2 = new OrderItemResponse();
        item2.setId(3L);
        item2.setPrice(new BigDecimal("120.0"));
        item2.setProductId(5L);
        item2.setQuantity(8);

        OrderItemResponse item3 = new OrderItemResponse();
        item3.setId(5L);
        item3.setPrice(new BigDecimal("12.0"));
        item3.setProductId(5L);
        item3.setQuantity(8);

        order = new OrderResponse();
        order.setCustomerName("name");
        order.setStreetAdress("adress");
        order.setCity("city");
        order.setCard(true);
        order.setNameOfCard("name sff");
        //n
        order.setOrderPrice(new BigDecimal("1056.0"));

        order.setOrderItems(List.of(item1, item2));

        order1 = new OrderResponse();
        order1.setCustomerName("name");
        order1.setStreetAdress("adress2");
        order1.setCity("city2");
        order1.setCard(false);
        order1.setNameOfCard("name second");
//1
        order1.setOrderPrice(new BigDecimal("96.0"));
//2
        order1.setOrderItems(List.of(item3));}
    @Test
    @WithMockUser(username = "username")
    void getOrders_returnsUserOrders() throws Exception {

        List<OrderResponse> orders = List.of(order, order1);

        when(orderService.getUserOrders(username))
                .thenReturn(orders);

        mockMvc.perform(
                        get("/api/orders/my-orders")
                )
                /**
                 * mockMvi.perform(get ("/api/oders/my-orders"))
                 * .andExpect(jsonPath("$").isArray())
                 * .andExpect(jsonPath("$".length()".value(2))
                 *
                 * .andExpect(jsonPath("$[0].customerName").value(name))
                 * andExpect(jsonPath("$[0].streetAdress").value("sdfgh"))
                 *
                 *
                 * .andExpect(jsonPath($[1].customerName))
                 */

                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].customerName").value("name"))
                .andExpect(jsonPath("$[0].streetAdress").value("adress"))
                .andExpect(jsonPath("$[0].city").value("city"))
                .andExpect(jsonPath("$[0].card").value(true))
                .andExpect(jsonPath("$[0].nameOfCard").value("name sff"))
                .andExpect(jsonPath("$[0].orderPrice").value(1056.0))

                .andExpect(jsonPath("$[0].orderItems").isArray())
                .andExpect(jsonPath("$[0].orderItems.length()").value(2))

                .andExpect(jsonPath("$[0].orderItems[0].id").value(1L))
                .andExpect(jsonPath("$[0].orderItems[0].price").value(12.0))
                .andExpect(jsonPath("$[0].orderItems[0].productId").value(5))
                .andExpect(jsonPath("$[0].orderItems[0].quantity").value(8))

                .andExpect(jsonPath("$[0].orderItems[1].id").value(3L))
                .andExpect(jsonPath("$[0].orderItems[1].price").value(120.0))
                .andExpect(jsonPath("$[0].orderItems[1].productId").value(5))
                .andExpect(jsonPath("$[0].orderItems[1].quantity").value(8))

                /**
                 * .andExpect(jsonPath("$[0].orderItems[1].id").value(1)
                 * .andExpect(jsonPath("$[0].orderITems[1].quantity").value(hi))
                 .andExpect(jsonPath("$[0].orderITems[1]."))

                 .andExpect(jsonPath("$")orderPrice[30.9])

                 */

                .andExpect(jsonPath("$[1].customerName").value("name"))
                .andExpect(jsonPath("$[1].streetAdress").value("adress2"))
                .andExpect(jsonPath("$[1].city").value("city2"))
                .andExpect(jsonPath("$[1].card").value(false))
                .andExpect(jsonPath("$[1].nameOfCard").value("name second"))
                .andExpect(jsonPath("$[1].orderPrice").value(96.0))
                .andExpect(jsonPath("$[1].orderItems").isArray())
                .andExpect(jsonPath("$[1].orderItems.length()").value(1))

                .andExpect(jsonPath("$[1].orderItems[0].id").value(5L))
                .andExpect(jsonPath("$[1].orderItems[0].price").value(12.0))
                .andExpect(jsonPath("$[1].orderItems[0].productId").value(5))
                .andExpect(jsonPath("$[1].orderItems[0].quantity").value(8));
    }

    }
