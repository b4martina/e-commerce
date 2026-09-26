package com.example.e_commerce.repository;


import com.example.e_commerce.model.Product;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.example.e_commerce.model.Category.ART;

@DataJpaTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.flyway.enabled=false"
})
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    //public ProductRepositoryTest(){}

    @Test
    public void ProductRepository_SaveAll_returnSavedProducts(){
        Product product = Product.builder()
                .name("prod")
                .description("descccc")
                .price(BigDecimal.valueOf(120))
                .stockQuantity(123L)
                .category(ART)
                .createdAt(LocalDate.now())
                .build();
        Product savedProduct = productRepository.save(product);
        Assertions.assertThat(savedProduct).isNotNull();
        Assertions.assertThat(savedProduct.getId()).isGreaterThan(0L);
    }

    @Test
    public void ProductRepository_existsByName_ReturnBoolean (){
        Product product = Product.builder().name("preod").description("deswergcccc").price(BigDecimal.valueOf(120))                .createdAt(LocalDate.now())
                .stockQuantity(123L).category(ART).build();
    productRepository.save(product);
    Boolean productReturn = productRepository.existsByName(product.getName());
        Assertions.assertThat(productReturn).isTrue();
    }
}
