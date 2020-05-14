package com.miu.onlinemarket.service;

import java.util.Optional;

import com.miu.onlinemarket.domain.Order;

public interface OrderService {

	public Order save(Order order);

	public Optional<Order> findById(Long id);
}
