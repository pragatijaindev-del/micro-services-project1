package com.example.order_service.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {
//Calling product service to reduce stock and will throw exception if order is insufficient
    @PostMapping("/products/{id}/reduce")
    String reduceProductStock(@PathVariable Long id,
                       @RequestParam Integer qty);
}
