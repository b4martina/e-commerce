package com.example.e_commerce.service;


import com.example.e_commerce.dto.OrderItemRequest;
import com.example.e_commerce.dto.OrderRequest;
import com.example.e_commerce.dto.OrderResponse;
import com.example.e_commerce.model.Orders;
import com.example.e_commerce.model.OrderItems;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.model.User;
import com.example.e_commerce.repository.OrderRepository;
import com.example.e_commerce.repository.ProductRepository;
import com.example.e_commerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {


    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository= userRepository;
    }

    @Transactional
    public Orders createOrder (OrderRequest orderRequest, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found"));

        Orders order = new Orders();
        order.setBuyer(user);
        order.setCustomerName(orderRequest.getCustomerName());
        order.setStreetAdress(orderRequest.getStreetAdress());
        order.setAdressLine2(orderRequest.getAdressLine2());
        order.setPostalCode(orderRequest.getPostalCode());
        order.setCity(orderRequest.getCity());
        order.setCard(orderRequest.isCard());
        order.setNameOfCard(orderRequest.getNameOfCard());
        order.setCardNumber(orderRequest.getCardNumber());
        order.setExpirationDate(orderRequest.getExpirationDate());
        order.setSecurityCode(orderRequest.getSecurityCode());

        for (OrderItemRequest orderItemRequest : orderRequest.getItems()) {
            Product product = productRepository.findById(orderItemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("cant order this product"));

            int quantity = orderItemRequest.getQuantity();

            if (product.getStockQuantity() < quantity) {
                throw new RuntimeException("Not enough stock is  available for this product");
            }
                product.setStockQuantity(product.getStockQuantity() - quantity);

                OrderItems orderItem = new OrderItems();

                orderItem.setProduct(product);
                orderItem.setQuantity(quantity);
                orderItem.setPrice(product.getPrice());
                orderItem.setOrder(order);
            }
            return orderRepository.save(order);
        }




        public List<OrderResponse> getUserOrders ( String username){

        User user = userRepository.findByUsername(username).
                orElseThrow(() -> new RuntimeException( " user can not access the list "));

            List<Orders> orders = orderRepository.findByBuyerId(user.getId());


        if(orders.isEmpty()){
            throw new RuntimeException("couldnt find orders");
        }
            List <OrderResponse> orderResponse = new ArrayList<>();
            for (Orders order : orders ){
                OrderResponse or= new OrderResponse();


                or.setCustomerName(order.getCustomerName());
                or.setStreetAdress(order.getStreetAdress());
                or.setCity(order.getCity());
                or.setCard(order.isCard());
                or.setNameOfCard(order.getNameOfCard());

                orderResponse.add(or);

            }
            return orderResponse;


        }






}
