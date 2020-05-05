package com.miu.onlinemarket.service;

import com.miu.onlinemarket.domain.Order;
import com.miu.onlinemarket.domain.Product;

public interface OrderService {

    public Order save(Order order);
    public Order findById(Long id);
}
