package com.example.e_commerce.controller;


import com.example.e_commerce.dto.ProductRequest;
import com.example.e_commerce.dto.ProductResponse;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.service.AdminService;
import com.example.e_commerce.service.ProductService;
import jakarta.security.auth.message.config.AuthConfig;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private  final ProductService productService;
    private final AdminService adminService;

    public ProductController(ProductService productService, AdminService adminService) {
        this.productService = productService;
        this.adminService = adminService;
    }

    @PostMapping("/create-product")
    public ResponseEntity <?> createProduct(Authentication authentication, @RequestBody ProductRequest productRequest){
        String username = authentication.getName();

        Product product = productService.createProduct( username, productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity <?> getProductById(@PathVariable Long id){

        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }



}
