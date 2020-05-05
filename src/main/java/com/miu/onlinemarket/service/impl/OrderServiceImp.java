package com.miu.onlinemarket.service.impl;


import com.miu.onlinemarket.domain.Order;
import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.repository.OrderRepository;
import com.miu.onlinemarket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findOrderById(id);
    }
}
