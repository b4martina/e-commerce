package com.example.e_commerce.controller;


import com.example.e_commerce.dto.ProductRequest;
import com.example.e_commerce.dto.ProductResponse;
import com.example.e_commerce.model.Category;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.service.AdminService;
import com.example.e_commerce.service.ProductService;
import jakarta.security.auth.message.config.AuthConfig;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/pageable")
    public ResponseEntity<Page<ProductResponse>> pageable(@RequestParam int size, @RequestParam int page){
        Page <ProductResponse> products = productService.getPaginatedProducts(size , page);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category")
    public List<ProductResponse> filterByCategory (@RequestParam(required = false) Category category){
        return productService.getCategorizedProducts(category);

    }

    @GetMapping("/categorie")
    public List<ProductResponse> filterByCategory1 (@RequestParam(required = false) String category){
        return productService.getCategorizedProducts1(category);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct (@PathVariable Long id, @RequestBody ProductRequest productRequest, Authentication authentication){

        String username = authentication.getName();


        return ResponseEntity.ok( productService.updatedProduct(id, productRequest, username));

    }





}
