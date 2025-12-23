package com.example.order_service;

import com.example.order_service.entity.Order;
import com.example.order_service.exception.BusinessException;
import com.example.order_service.feign.ProductClient;
import com.example.order_service.feign.UserClient;
import com.example.order_service.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private ProductClient productClient;

    @Override
    @CircuitBreaker(name = "productService", fallbackMethod = "orderFallback")
    public Order createOrder(Order order) {

        // 1️⃣ Stock validation (FIXED)
        if (order.getStock() == null || order.getStock() <= 0) {
            throw new BusinessException("Order stock must be greater than zero");
        }

        // 2️⃣ Validate user
        userClient.validateUser(order.getUserId());

        // 3️⃣ Reduce product stock
        productClient.reduceProductStock(
                order.getProductId(),
                order.getStock()
        );

        // 4️⃣ Save order
        order.setStatus("CONFIRMED");
        return orderRepository.save(order);
    }

    // 5️⃣ Circuit breaker fallback (SIGNATURE CORRECT)
    public Order orderFallback(Order order, Throwable t) {
        order.setStatus("FAILED");
        return order;
    }
}
