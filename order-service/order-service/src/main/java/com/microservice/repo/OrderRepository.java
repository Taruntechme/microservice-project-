package com.microservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.model.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
