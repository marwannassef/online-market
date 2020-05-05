package com.miu.onlinemarket.repository;

import com.miu.onlinemarket.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public Order findOrderById(Long id);
}
