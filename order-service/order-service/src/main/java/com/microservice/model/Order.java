package com.microservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String product;
	private int quantity;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Order() {
		super();
	}
	public Order(Long id, String product, int quantity) {
		super();
		this.id = id;
		this.product = product;
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", product=" + product + ", quantity=" + quantity + "]";
	}
	
	
}
