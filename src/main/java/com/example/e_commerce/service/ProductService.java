package com.example.e_commerce.service;

import com.example.e_commerce.dto.ProductRequest;
import com.example.e_commerce.dto.ProductResponse;
import com.example.e_commerce.dto.StockRequest;
import com.example.e_commerce.model.Category;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.model.User;
import com.example.e_commerce.repository.ProductRepository;
import com.example.e_commerce.repository.UserRepository;
import com.example.e_commerce.security.ResourceException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

            /*Product product = productRepository.findById(id).
                orElseThrow(()->new RuntimeException("product not found"));

        if (!product.isActive()) {
            throw new RuntimeException("Product not found");
        }*/
        Product product = productRepository.findById(id)
                .filter(Product::isActive)
                .orElseThrow(() ->
                        new ResourceException("Product not found"));

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

    public List<ProductResponse> getCategorizedProducts (Category category){
        List<Product> products;

        if (category == null ){
            products= productRepository.findAll();
        } else {
            products = productRepository.categoryFilteredProducts(category.name());
        }

        List <ProductResponse> productResponsesList = new ArrayList<>();

        for (Product product : products){
            ProductResponse pr = new ProductResponse();
            pr.setName(product.getName());
            pr.setCategory(product.getCategory());
            pr.setDescription(product.getDescription());
            pr.setStockQuantity(product.getStockQuantity());
            pr.setPrice(product.getPrice());

            productResponsesList.add(pr);
        }
        return productResponsesList;
    }



    public List<ProductResponse> getCategorizedProducts1 (String category){
        List<Product> products;

        if (category == null ){
            products= productRepository.findAll();
        } else {
            products = productRepository.findByCategory(category);
        }

        List <ProductResponse> productResponsesList = new ArrayList<>();

        for (Product product : products){
            ProductResponse pr = new ProductResponse();
            pr.setName(product.getName());
            pr.setCategory(product.getCategory());
            pr.setDescription(product.getDescription());
            pr.setStockQuantity(product.getStockQuantity());
            pr.setPrice(product.getPrice());
            productResponsesList.add(pr);
        }
        return productResponsesList;
    }

    public Product updatedProduct(Long id, ProductRequest productRequest, String username){

        Product product= productRepository.findById(id).orElseThrow(()-> new RuntimeException("product not found, can not be updated"));
        if (!product.getProductOwner().getUsername().equals(username)){
            throw new RuntimeException("This product can not be modified");
            }
        product.setCategory(productRequest.getCategory());
        product.setDescription(productRequest.getDescription());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setPrice(productRequest.getPrice());

        if (productRepository.existsByName(productRequest.getName())) {
            throw new IllegalArgumentException("A product with this name already exists");
        }
        product.setName(productRequest.getName());
        return product;
}

@Transactional
public Product adjustStock (Long id, StockRequest stockQuantityRequest, String username ){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("cant adjust"));

        if (!product.getProductOwner().getUsername().equals(username)){
            throw  new RuntimeException("cant adjust stock");}

            long newStock = product.getStockQuantity() + stockQuantityRequest.getStockQuantity();

            if (newStock <0 ){
                throw new IllegalArgumentException("Stock can not be negative. Check the value of the added stock!");
            }
            product.setStockQuantity(newStock);
            return productRepository.save(product);
}


@Transactional
    public void deleteProduct (Long id, String username){
        Product product= productRepository.findById(id).orElseThrow(()->new RuntimeException("can not delete this product"));
        if (!product.getProductOwner().getUsername().equals(username)) {
        throw new RuntimeException("You cannot delete this product");
    }
    productRepository.delete(product);
}

//@Transactional
    public List<ProductResponse> getAllProducts (){
    List <Product> products = productRepository.findAll();

            List <ProductResponse> productResponse = new ArrayList<>();
            for (Product product: products ){
                if (product.isActive()){
                ProductResponse pr = new ProductResponse();
                pr.setName(product.getName());
                pr.setCategory(product.getCategory());
                pr.setDescription(product.getDescription());
                pr.setStockQuantity(product.getStockQuantity());
                pr.setPrice(product.getPrice());

                productResponse.add(pr);
            }}
          return productResponse;
    }





}



