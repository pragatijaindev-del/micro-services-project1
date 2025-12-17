package com.example.product_service;
import com.example.product_service.entity.Product;

import java.util.List;
public interface ProductService {

    Product createProduct(Product product);

    Product getProductById(Long id);

    List<Product> getAllProducts();

    String reduceProductStock(Long id, Integer qty);
}


