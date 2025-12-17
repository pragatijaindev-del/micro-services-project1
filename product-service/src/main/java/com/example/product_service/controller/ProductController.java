package com.example.product_service.controller;

import com.example.product_service.ProductService;
import com.example.product_service.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return service.createProduct(product);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @PostMapping("/{id}/reduce")
    public String reduceProductStock(@PathVariable Long id,
                                     @RequestParam Integer qty) {
        return service.reduceProductStock(id, qty);
    }
}
