package com.miu.onlinemarket.service;

import com.miu.onlinemarket.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();
    List<Product> findByName(String name);
    Product save(Product product);
    void delete(long id);
}
