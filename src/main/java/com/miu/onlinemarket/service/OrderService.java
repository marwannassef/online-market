package com.miu.onlinemarket.service;

import com.miu.onlinemarket.domain.Order;

import java.util.Optional;

public interface OrderService {

    public Order save(Order order);
    public Optional<Order> findById(Long id);
}
