package com.example.product_service;
import com.example.product_service.entity.Product;
import com.example.product_service.exception.BusinessException;
import com.example.product_service.feign.UserClient;
import com.example.product_service.repository.ProductRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//Product Service manages inventory operations.It handles product persisten, stock validation,and stock reduction for order processing.

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private UserClient userClient;

    @Override
    public Product createProduct(Product product) {
        return repo.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    @Override
    @CircuitBreaker(name = "userService", fallbackMethod = "stockFallback")
    public String reduceProductStock(Long id, Integer qty) {

        Product p = repo.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Product not found with id: " + id));

        if (p.getStock() < qty) {
            throw new BusinessException("Insufficient stock for product id: " + id);
        }

        // User service call to validate Feign + CB integration
        userClient.validateUser(1L);

        p.setStock(p.getStock() - qty);
        repo.save(p);

        return "Stock reduced successfully";
    }

    public String stockFallback(Long id, Integer qty, Throwable t) {
        return "User service unavailable, stock update skipped";
    }
}