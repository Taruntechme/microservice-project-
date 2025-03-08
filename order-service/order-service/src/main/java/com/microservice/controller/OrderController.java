package com.microservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.model.Order;
import com.microservice.repo.OrderRepository;

@RestController
@RequestMapping("orders")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;
	
	public List<Order> getAllOrders(){
		return orderRepository.findAll();
	}
	
	public Order createOrder(@RequestBody Order order) {
		return orderRepository.save(order);
	}
}
