package com.miu.onlinemarket.service.impl;

import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.repository.ProductRepository;
import com.miu.onlinemarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> searchByName(String name) {
        return productRepository.SearchByName(name);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }


    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(long id) {

        productRepository.deleteById(id);
    }
}
