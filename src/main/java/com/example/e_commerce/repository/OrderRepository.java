package com.example.e_commerce.repository;

import com.example.e_commerce.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {

      List<Orders> findByBuyerId (Long Id);




}
