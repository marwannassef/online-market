package com.miu.onlinemarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.onlinemarket.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	public Order findOrderById(Long id);
}
