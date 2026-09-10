package com.example.e_commerce.repository;

import com.example.e_commerce.model.Category;
import com.example.e_commerce.model.Product;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);

    List <Product> findAll();
    List<Product> findByCategory(String category);

    @Query(nativeQuery = true,
    value = "SELECT * from products WHERE category = :category")
    List<Product> categoryFilteredProducts (@Param ("category") String category);

    Optional<Product> findById(Long id);

    Optional <Product> findByActive(boolean active);
}
