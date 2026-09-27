package com.example.e_commerce.controller;


import com.example.e_commerce.dto.ProductRequest;
import com.example.e_commerce.dto.ProductResponse;
import com.example.e_commerce.model.Category;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.service.AdminService;
import com.example.e_commerce.service.CustomUserDetailService;
import com.example.e_commerce.service.JWTService;
import com.example.e_commerce.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ProductService productService;
    @MockitoBean
    private JWTService jwtService;
    @MockitoBean
    private CustomUserDetailService customUserDetailService;
    @MockitoBean
    private AdminService adminService;


    private Product product;
    private ProductRequest productRequest;
    private ProductResponse productResponse;
    @BeforeEach
    public void setUp(){
        productResponse = new ProductResponse();
        productResponse.setName("qwert");
        productResponse.setCategory(Category.BAGS);
        productResponse.setPrice(BigDecimal.valueOf(1234));
        productResponse.setDescription("erghgfds wefgb");
        productResponse.setStockQuantity(123L);
        productResponse.setId(1L);
    }

    @Test
    @WithMockUser(username = "username")

    public void GetProduct_usingId() throws Exception{
    when (productService.getProductById(1L)).thenReturn(productResponse);
        mockMvc.perform(
                        get("/api/products/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("qwert"))
                .andExpect(jsonPath("$.price").value(1234))
                .andExpect(jsonPath("$.description").value("erghgfds wefgb"))
                .andExpect(jsonPath("$.stockQuantity").value(123))
                .andExpect(jsonPath("$.category").value("BAGS"));

    }


/**
 * public ResponseEntity <?> getProductById(@PathVariable Long id) throws ProductNotAvailableException {
 *
 *         ProductResponse product = productService.getProductById(id);
 *         return ResponseEntity.status(HttpStatus.OK).body(product);
 *     }
 */


}
