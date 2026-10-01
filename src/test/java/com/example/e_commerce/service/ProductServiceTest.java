package com.example.e_commerce.service;
import com.example.e_commerce.dto.ProductResponse;
import com.example.e_commerce.model.Category;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.model.User;
import com.example.e_commerce.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService  productService;
    @Mock
    private ProductRepository productRepository;

   /* @Test
    public void ProductService_deleteProduct (){
       //arrange
        Long productId= 1L;
        String username = " usern";
        User user = new User();
        user.setUsername(username);

        Product product = new Product();
        product.setId(productId);
        product.setProductOwner(user);
        when (productRepository.findById(productId)).thenReturn(Optional.of(product));
        //act
        productService.deleteProduct(productId, username);
        //assert
        verify(productRepository).deleteById(productId);
        verify(productRepository).delete(product);
    }
    @Test
    public void deleteProd_noResponse(){
        //arrange
        Long productId=3L;
        String username = "qwerty";
        User user= new User();
        user.setUsername(username);
        //act
        when (productRepository.findById(productId)).thenReturn(Optional.empty());
        //assert
        Assertions.assertThatThrownBy(()->productService.deleteProduct(productId,username))
                .isInstanceOf(RuntimeException.class).hasMessage("can not delete product");
        verify(productRepository.findById(productId));
        verify(productRepository, never()).delete(any(Product.class));
    }*/
    @Test
    public void getAllProducts_ReturnOnlyActiveProducts
            (){
        //arrange
        Product product1= new Product();
        product1.setPrice(BigDecimal.valueOf(120));
        product1.setName("product1");
        product1.setCategory(Category.ACCESORIES);
        product1.setDescription("qwertyuhgfd wertyju wertyuj");
        product1.setActive(false);
        product1.setStockQuantity(123L);
        product1.setId(1L);

        Product product2= new Product();
        product2.setPrice(BigDecimal.valueOf(120));
        product2.setName("product2");
        product2.setCategory(Category.ACCESORIES);
        product2.setDescription("qwertyuhgfd wertyju wertyuj");
        product2.setActive(true);
        product2.setStockQuantity(123L);
        product2.setId(2L);

        when(productRepository.findAll()). thenReturn(List.of(product1, product2));

        //act
        List <ProductResponse> productList = productService.getAllProducts();

        //assert
        assertThat(productList).hasSize(1);
        verify(productRepository).findAll();


    }

}
