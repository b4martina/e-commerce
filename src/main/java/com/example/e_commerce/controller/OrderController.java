package com.example.e_commerce.controller;


import com.example.e_commerce.dto.OrderRequest;
import com.example.e_commerce.dto.OrderResponse;
import com.example.e_commerce.model.Orders;
import com.example.e_commerce.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping
    public Orders createOrder (@Valid @RequestBody OrderRequest orderRequest, Authentication authentication){
        String username = authentication.getName();
        return orderService.createOrder(orderRequest, username);
    }



    @GetMapping("/my-orders")
    public List<OrderResponse> getAllUserOrders ( Authentication authentication){

        String username = authentication.getName();

        return orderService.getUserOrders(username);

    }


}