package com.miu.onlinemarket.service;

import com.miu.onlinemarket.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();
    List<Product> searchByName(String name);
    Optional<Product> findById(Long id);
    Product save(Product product);
    void delete(long id);
}
