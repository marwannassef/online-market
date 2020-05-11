package com.miu.onlinemarket.service;

import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.exceptionhandling.ResourceNotFoundException;

import java.util.List;

public interface ProductService {

    List<Product> findAll();
    List<Product> searchByName(String name);
    Product findById(Long id) throws ResourceNotFoundException;
    Product save(Product product);
    void delete(Product product);
    public Product update(Product product,Long id) throws ResourceNotFoundException;
}
