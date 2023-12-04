package com.microservices.Order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.microservices.Order.dto.OrderRequest;
import com.microservices.Order.dto.ProductResponse;
import com.microservices.Order.entity.Order;
import com.microservices.Order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    
    @GetMapping("/{orderId}/product")
    public ProductResponse getProductDetailsForOrder(@PathVariable Long orderId) {
        // Fetch product details for the order using the OrderService
    	System.out.println("hello=-");
        return orderService.getProductByIdFromProductService(orderId);
    }
    
    
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest  orderRequest) {
    	System.out.println("hello create order call");
        return orderService.createOrder(orderRequest);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody OrderRequest orderRequest) {
        return orderService.updateOrder(id, orderRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
