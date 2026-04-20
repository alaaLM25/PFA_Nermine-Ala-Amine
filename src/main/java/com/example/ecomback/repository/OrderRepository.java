package com.example.ecomback.repository;

import com.example.ecomback.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Override
    @EntityGraph(attributePaths = {"user", "items", "items.product"})
    List<Order> findAll();
    
    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    Double calculateTotalRevenue();

    @Query("SELECT SUM(oi.quantity) FROM OrderItem oi")
    Long countTotalProductsSold();
}
