package com.example.e_commerce.service;

import com.example.e_commerce.dto.ProductRequest;
import com.example.e_commerce.dto.ProductResponse;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.model.User;
import com.example.e_commerce.repository.ProductRepository;
import com.example.e_commerce.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Product createProduct (String username, ProductRequest productRequest){
        User productOwner = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("This user can not creae a blog"));

        if (productRequest.getStockQuantity() < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");}
        if (productRepository.existsByName(productRequest.getName())) {
            throw new IllegalArgumentException("A product with this name already exists");
        }
        Product product = new Product();

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
        product.setCreatedAt(LocalDate.now());
        product.setActive(true);
        product.setProductOwner(productOwner);

        return productRepository.save(product) ;

    }

    public ProductResponse getProductById( Long id){

            Product product = productRepository.findById(id).
                orElseThrow(()->new RuntimeException("product not found"));

        if (!product.isActive()) {
            throw new RuntimeException("Product not found");
        }


        ProductResponse pr = new ProductResponse();

        pr.setName(product.getName());
        pr.setDescription(product.getDescription());
        pr.setPrice(product.getPrice());
        pr.setStockQuantity(product.getStockQuantity());
        pr.setCategory(product.getCategory());

        return pr;
    }

    public Page<ProductResponse> getPaginatedProducts (int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        Page <Product> products = productRepository.findAll(pageable);

        return products.map(product-> new ProductResponse(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCategory()
        ));
    }





}
