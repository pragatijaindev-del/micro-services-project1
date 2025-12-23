package com.example.order_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


// Order Entity represents an order placed by User

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private Long productId;
    @NotNull
    private Integer stock;
    private String status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Order() {
		;
	}
	public Order(Long id, @NotNull Long userId, @NotNull Long productId, @NotNull Integer stock, String status) {
		super();
		this.id = id;
		this.userId = userId;
		this.productId = productId;
		this.stock = stock;
		this.status = status;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", userId=" + userId + ", productId=" + productId + ", stock=" + stock + ", status="
				+ status + "]";
	}
	
    
}
    