package com.example.e_commerce.service;


import com.example.e_commerce.dto.*;
import com.example.e_commerce.exceptions.NoPermissionException;
import com.example.e_commerce.exceptions.OrderNotAvailableException;
import com.example.e_commerce.exceptions.ProductNotAvailableException;
import com.example.e_commerce.exceptions.UnauthorizedException;
import com.example.e_commerce.model.Orders;
import com.example.e_commerce.model.OrderItems;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.model.User;
import com.example.e_commerce.repository.OrderRepository;
import com.example.e_commerce.repository.ProductRepository;
import com.example.e_commerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Builder
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
    public Orders createOrder (OrderRequest orderRequest, String username) throws ProductNotAvailableException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("user not found")); //custom exception
        Orders order = new Orders();
        order.setBuyer(user);
        order.setCustomerName(orderRequest.getCustomerName());
        order.setOrderDescription(order.getOrderDescription());

        order.setStreetAdress(orderRequest.getStreetAdress());
        order.setAdressLine2(orderRequest.getAdressLine2());
        order.setPostalCode(orderRequest.getPostalCode());
        order.setCity(orderRequest.getCity());
        order.setCard(orderRequest.isCard());
        order.setNameOfCard(orderRequest.getNameOfCard());
        order.setCardNumber(orderRequest.getCardNumber());
        order.setExpirationDate(orderRequest.getExpirationDate());
        order.setSecurityCode(orderRequest.getSecurityCode());

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItemRequest orderItemRequest : orderRequest.getItems()) {
            Product product = productRepository.findById(orderItemRequest.getProductId())
                    .orElseThrow(() -> new ProductNotAvailableException("You can not order this product"));

            int quantity = orderItemRequest.getQuantity();

            if (product.getStockQuantity() < quantity) {
                throw new ProductNotAvailableException("Insufficient stock available for this product");
            }

            if (quantity <= 0) {
                throw new ProductNotAvailableException("quantity must be greater than 0");}

            BigDecimal itemTotal =
                    product.getPrice()
                            .multiply(BigDecimal.valueOf(quantity));

        totalPrice  = totalPrice.add(itemTotal);

        product.setStockQuantity(product.getStockQuantity() - quantity);
                OrderItems orderItem = new OrderItems();
                orderItem.setProduct(product);
                orderItem.setQuantity(quantity);
                orderItem.setPrice(product.getPrice());
                orderItem.setOrder(order);
                order.getOrderItems().add(orderItem);
                order.setOrderPrice(totalPrice);
        }
        return orderRepository.save(order);
        }

        public List<OrderResponse> getUserOrders ( String username) throws OrderNotAvailableException {

        User user = userRepository.findByUsername(username).
                orElseThrow(() -> new NoPermissionException( "You can not access this list "));
            List<Orders> orders = orderRepository.findByBuyerId(user.getId());

        if(orders.isEmpty()){
            throw new OrderNotAvailableException("This user has not made any orders");
        }
            List <OrderResponse> orderResponse = new ArrayList<>();
            for (Orders order : orders ){
                OrderResponse or= new OrderResponse();
                or.setOrderDescription(order.getOrderDescription());
                or.setCustomerName(order.getCustomerName());
                or.setStreetAdress(order.getStreetAdress());
                or.setCity(order.getCity());
                or.setCard(order.isCard());
                or.setNameOfCard(order.getNameOfCard());
                or.setOrderPrice(order.getOrderPrice());
                or.setOrderItems(
                        order.getOrderItems().stream()
                                .map(item -> {
                                    OrderItemResponse itemResponse = new OrderItemResponse();

                                    itemResponse.setId(item.getId());
                                    itemResponse.setQuantity(item.getQuantity());
                                    itemResponse.setPrice(item.getPrice());

                                    itemResponse.setProductId(item.getProduct().getId());
                                    return itemResponse;
                                })
                                .toList()
                );
                // caution here, order items is a list, will it be there
                orderResponse.add(or);
            }
            return orderResponse;}
/*
    public CartResponse previewOrder(OrderRequest orderRequest)
            throws ProductNotAvailableException {

        List<CartItemResponse> previewItems = new ArrayList<>();

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItemRequest orderItemRequest : orderRequest.getItems()) {

            Product product = productRepository.findById(
                    orderItemRequest.getProductId()
            ).orElseThrow(() ->
                    new ProductNotAvailableException(
                            "You can not order this product"
                    )
            );

            int quantity = orderItemRequest.getQuantity();

            if (quantity <= 0) {
                throw new ProductNotAvailableException(
                        "Quantity must be greater than 0"
                );
            }

            if (product.getStockQuantity() < quantity) {
                throw new ProductNotAvailableException(
                        "Insufficient stock available for product: "
                                + product.getName()
                );
            }

            BigDecimal itemTotal =
                    product.getPrice()
                            .multiply(BigDecimal.valueOf(quantity));

            totalPrice = totalPrice.add(itemTotal);

            CartItemResponse previewItem =
                    new CartItemResponse(
                            product.getId(),
                            product.getName(),
                            quantity,
                            product.getPrice(),
                            itemTotal
                    );
            previewItems.add(previewItem);
        }
        return new CartResponse(
                previewItems,
                totalPrice
        );
    }*/



    public CartResponse previewOrder(CartRequest cartRequest)  throws ProductNotAvailableException {
        List<CartItemResponse> previewItems = new ArrayList<>();

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CartItemRequest cartItemRequest : cartRequest.getItems()) {

            Product product = productRepository.findById(
                    cartItemRequest.getProductId()
            ).orElseThrow(() ->
                    new ProductNotAvailableException(
                            "You can not order this product"));

            int quantity = cartItemRequest.getQuantity();

            if (quantity <= 0) {
                throw new ProductNotAvailableException(
                        "Quantity must be greater than 0");
            }

            if (product.getStockQuantity() < quantity) {
                throw new ProductNotAvailableException(
                        "Insufficient stock available for product: "
                                + product.getName()
                );
            }

            BigDecimal itemTotal = product.getPrice() .multiply(BigDecimal.valueOf(quantity));

            totalPrice = totalPrice.add(itemTotal);

            CartItemResponse previewItem =
                    new CartItemResponse(
                            product.getId(),
                            product.getName(),
                            quantity,
                            product.getPrice(),
                            itemTotal
                    );
            previewItems.add(previewItem);
        }
        return new CartResponse(
                previewItems,
                totalPrice
        );
    }

/*
    public CartResponse productsPriceCalculation (OrderRequest orderRequest) throws ProductNotAvailableException{
        List<CartItemResponse> previewCartItems = new ArrayList<>();

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItemRequest orderItemRequest : orderRequest.getItems() ){
       Product product = productRepository.findById(orderItemRequest.getProductId()).orElseThrow(()->new ProductNotAvailableException("you can not order this product"));
            int quantity = orderItemRequest.getQuantity();

            if (quantity <= 0){
                throw  new ProductNotAvailableException("Quantity must be greater than 0 ");
            }
            if (product.getStockQuantity() < quantity) {
                throw new ProductNotAvailableException(
                        "Insufficient stock available for product: "
                                + product.getName());
            }
            BigDecimal itemTotal =
                    product.getPrice()
                            .multiply(BigDecimal.valueOf(quantity));
            totalPrice = totalPrice.add(itemTotal);

            CartResponse previewCartItem = new CartResponse(
                    product.getId(),
                    product.getPrice(),
                    quantity,
                    product.getPrice(),
                    itemTotal
            );
            previewCartItems.add(previewCartItem);
        }
        return new CartResponse(
                previewItems,
                totalPrice
        );
    }*/
}
