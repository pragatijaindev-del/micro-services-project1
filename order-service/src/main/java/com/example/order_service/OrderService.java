package com.example.order_service;

import com.example.order_service.entity.Order;
//Defines contract for Order Creation
public interface OrderService {
    Order createOrder(Order order);
}
