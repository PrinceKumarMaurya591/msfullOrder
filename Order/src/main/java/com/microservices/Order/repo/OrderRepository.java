package com.microservices.Order.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.Order.entity.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Add custom queries if needed
}
