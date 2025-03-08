# Spring Boot Microservices Project

This guide will walk you through the steps to create a **Spring Boot Microservices** project from scratch. The project will include the following components:

- **Project structure**
- **Dependencies**
- **Service creation**
- **Configuration**
- **Running and testing**

---

## **Step 1: Set Up Your Project**

We'll create a **Spring Boot Microservices** project with the following services:

1. **Eureka Server** (Service Discovery)
2. **API Gateway** (Routing and Authentication)
3. **Config Server** (Centralized Configuration)
4. **User Service** (Business Logic)
5. **Order Service** (Handles Orders)
6. **Database** (MySQL or H2)

---

## **Step 2: Create Projects Using Spring Boot Starter**

You can create the projects using **Spring Initializr** ([start.spring.io](https://start.spring.io/)) or manually using Maven/Gradle.

---

### **1. Eureka Server (Service Discovery)**

- **Project Name**: `eureka-server`
- **Type**: Spring Boot Starter Project

**Dependencies**:
- **Spring Boot Starter Web**
- **Spring Boot Starter Actuator**
- **Eureka Server**

#### **File Structure:**
```
eureka-server
 ├── src/main/java/com/example/eureka
 │   ├── EurekaServerApplication.java
 │   ├── application.properties
 ├── pom.xml
```

#### **`EurekaServerApplication.java`**
```java
package com.example.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

#### **`application.properties`**
```properties
server.port=8761
spring.application.name=eureka-server
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

---

### **2. API Gateway (Zuul or Spring Cloud Gateway)**

- **Project Name**: `api-gateway`

**Dependencies**:
- **Spring Boot Starter Web**
- **Spring Cloud Gateway**
- **Eureka Client**

#### **`ApiGatewayApplication.java`**
```java
package com.example.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
```

#### **`application.properties`**
```properties
server.port=8080
spring.application.name=api-gateway
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

spring.cloud.gateway.routes[0].id=user-service
spring.cloud.gateway.routes[0].uri=http://localhost:8081/
spring.cloud.gateway.routes[0].predicates=Path=/users/**

spring.cloud.gateway.routes[1].id=order-service
spring.cloud.gateway.routes[1].uri=http://localhost:8082/
spring.cloud.gateway.routes[1].predicates=Path=/orders/**
```

---

### **3. User Service**

- **Project Name**: `user-service`
- **Port**: 8081

**Dependencies**:
- **Spring Boot Starter Web**
- **Spring Boot Starter Data JPA**
- **Spring Boot Starter Actuator**
- **MySQL Driver**
- **Eureka Client**

#### **`UserServiceApplication.java`**
```java
package com.example.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
```

#### **`application.properties`**
```properties
server.port=8081
spring.application.name=user-service
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
spring.datasource.url=jdbc:mysql://localhost:3306/users_db
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
```

#### **User Entity (`User.java`)**
```java
package com.example.userservice.model;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    // Getters and Setters
}
```

#### **User Repository (`UserRepository.java`)**
```java
package com.example.userservice.repository;

import com.example.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
```

#### **User Controller (`UserController.java`)**
```java
package com.example.userservice.controller;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}
```

---

### **4. Order Service**

- **Project Name**: `order-service`
- **Port**: 8082

**Dependencies**:
- **Spring Boot Starter Web**
- **Spring Boot Starter Data JPA**
- **Spring Boot Starter Actuator**
- **MySQL Driver**
- **Eureka Client**

#### **Order Entity (`Order.java`)**
```java
package com.example.orderservice.model;

import jakarta.persistence.*;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String product;
    private int quantity;

    // Getters and Setters
}
```

#### **Order Repository (`OrderRepository.java`)**
```java
package com.example.orderservice.repository;

import com.example.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
```

#### **Order Controller (`OrderController.java`)**
```java
package com.example.orderservice.controller;

import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }
}
```

---

## **Step 3: Run the Microservices**

To start each service, follow these steps:

1. **Start Eureka Server**:
   ```bash
   mvn spring-boot:run
   ```

2. **Start API Gateway**:
   ```bash
   mvn spring-boot:run
   ```

3. **Start User Service**:
   ```bash
   mvn spring-boot:run
   ```

4. **Start Order Service**:
   ```bash
   mvn spring-boot:run
   ```

---

## **Step 4: Test the Microservices**

### Test API Endpoints:

**User Service**:
- `GET http://localhost:8081/users`
- `POST http://localhost:8081/users`

**Order Service**:
- `GET http://localhost:8082/orders`
- `POST http://localhost:8082/orders`

**API Gateway (Using Gateway Routing)**:
- `GET http://localhost:8080/users`
- `GET http://localhost:8080/orders`

---

This is a basic **microservices setup** with Eureka, API Gateway, and two services (User and Order). You can extend the project by adding more services, using Spring Config Server, and adding authentication and security features.

---

**GitHub Repository**: https://github.com/Taruntechme/microservice-project-.git

Here's a flow diagram representing the interaction between your API Gateway, Order Service, Eureka Server, and other microservices:

Flow Overview
Client (User) Request: The client sends a request to the API Gateway.
Service Discovery (Eureka Server): The API Gateway checks Eureka Server for available services.
Routing to Order Service: If the Order Service is registered, the request is forwarded.
Order Processing: The Order Service processes the request and may interact with other services like Inventory, Payment, etc.
Response to Client: The processed response is sent back via API Gateway.
Flow Diagram Components
📌 Eureka Server → Manages service registration and discovery.
📌 API Gateway → Routes requests based on service discovery.
📌 Order Service → Handles order-related operations.
📌 Other Services → Supporting services like Inventory, Payment, etc.
📌 Client → Sends requests and receives responses.

A simple **dotted line flow diagram** for a microservices architecture with an **Order Service** could look like this:

```
User
  |
  v
[API Gateway]
  |
  v
[Authentication Service] -----> [User Service]
  |
  v
[Order Service] -----> [Inventory Service]
  | 
  v
[Payment Service] -----> [Notification Service]
```

### Explanation:
- **Dotted lines** (----->) represent communication between services.
- The **User** interacts with the system via an **API Gateway**.
- Authentication is done first, then the order request is processed.
- The **Order Service** interacts with:
  - **Inventory Service** to check stock.
  - **Payment Service** to process payments.
  - **Notification Service** to send confirmations.
