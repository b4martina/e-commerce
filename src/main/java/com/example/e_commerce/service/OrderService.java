package com.example.e_commerce.service;


import com.example.e_commerce.dto.OrderRequest;
import com.example.e_commerce.dto.ProductResponse;
import com.example.e_commerce.model.Order;
import com.example.e_commerce.model.OrderItems;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.model.User;
import com.example.e_commerce.repository.OrderRepository;
import com.example.e_commerce.repository.ProductRepository;
import com.example.e_commerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Order createOrder (Long id, OrderRequest orderRequest, String username) {


        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("product not found"));


        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not foudn0"));

        int quantity = orderRequest.getQuantity();


        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }


        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Not enough stock");
        }

        product.setStockQuantity(
                product.getStockQuantity() - quantity
        );

        Order order= new Order();
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

        OrderItems orderItem = new OrderItems();

        orderItem.setQuantity(quantity);
        orderItem.setPrice(product.getPrice());
        orderItem.setProduct(product);
        orderItem.setOrder(order);

        order.getOrderItems().add(orderItem);

        return orderRepository.save(order);
    }

//fixed it, no ch*t <3



}
