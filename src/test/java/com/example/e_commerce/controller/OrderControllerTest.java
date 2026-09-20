package com.example.e_commerce.controller;

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



   // @Autowired
    //private ObjectMapper objectMapper;

    private String username;
    private OrderResponse order;
    private OrderResponse order1;

    @BeforeEach
    void setUp() {

        username = "username";

        order = new OrderResponse();
        order.setCustomerName("name");
        order.setStreetAdress("adress");
        order.setCity("city");
        order.setCard(true);
        order.setNameOfCard("name sff");

        order1 = new OrderResponse();
        order1.setCustomerName("name2");
        order1.setStreetAdress("adress2");
        order1.setCity("city2");
        order1.setCard(false);
        order1.setNameOfCard("name second");
    }

    @Test
    @WithMockUser(username = "username")
    void getOrders_returnsUserOrders()
            throws Exception {

        List<OrderResponse> orders = List.of(order, order1);

        when(orderService.getUserOrders(username))
                .thenReturn(orders);

        mockMvc.perform(
                        get("/api/orders/my-orders")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].customerName").value("name"))
                .andExpect(jsonPath("$[0].streetAdress").value("adress"))
                .andExpect(jsonPath("$[0].city").value("city"))
                .andExpect(jsonPath("$[0].card").value(true))
                .andExpect(jsonPath("$[0].nameOfCard").value("name sff"))

                .andExpect(jsonPath("$[1].customerName").value("name2"))
                .andExpect(jsonPath("$[1].streetAdress").value("adress2"))
                .andExpect(jsonPath("$[1].city").value("city2"))
                .andExpect(jsonPath("$[1].card").value(false))
                .andExpect(jsonPath("$[1].nameOfCard").value("name second"));
    }
}
