package com.example.ecomback.service;

import com.example.ecomback.entity.Order;
import com.example.ecomback.entity.OrderStatus;
import com.example.ecomback.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order updateOrderStatus(Long id, Map<String, String> statusMap) {
        Order order = getOrderById(id);
        if (statusMap.containsKey("status")) {
            String statusValue = statusMap.get("status");
            try {
                order.setStatus(OrderStatus.valueOf(statusValue));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                    "Invalid order status: '" + statusValue + "'. Allowed values: " + 
                    Arrays.toString(OrderStatus.values())
                );
            }
        }
        return orderRepository.save(order);
    }
}
