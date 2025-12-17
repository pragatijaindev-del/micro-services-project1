package com.example.order_service;

import com.example.order_service.entity.Order;
import com.example.order_service.exception.BusinessException;
import com.example.order_service.feign.ProductClient;
import com.example.order_service.feign.UserClient;
import com.example.order_service.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//It validates user, checks product stock,and creates the order.

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

        // Business validation: quantity must be valid
        if (order.getQuantity() == null || order.getQuantity() <= 0) {
            throw new BusinessException("Order quantity must be greater than zero");
        }

        // Validate user existence (throws exception if invalid)
        userClient.validateUser(order.getUserId());

        // Reduce product stock (throws exception if insufficient)
        productClient.reduceProductStock(order.getProductId(), order.getQuantity());

        // Save order after successful validations
        order.setStatus("confirmed");
        return orderRepository.save(order);
    }

    //Circuit breaker fallback when Product Service is unavailable.

    public Order orderFallback(Order order, Throwable t) {
        order.setStatus("Request Failed,order service is down");
        return order;
    }
}
