package com.miu.onlinemarket.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miu.onlinemarket.domain.Order;
import com.miu.onlinemarket.repository.OrderRepository;
import com.miu.onlinemarket.service.OrderService;

@Service
public class OrderServiceImp implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Order save(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public Optional<Order> findById(Long id) {
		return orderRepository.findById(id);
	}
}
