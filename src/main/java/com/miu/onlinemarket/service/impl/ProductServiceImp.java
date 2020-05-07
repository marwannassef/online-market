package com.miu.onlinemarket.service.impl;

import com.miu.onlinemarket.domain.Buyer;
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
    public void delete(Product product) {

        productRepository.delete(product);
    }

    @Override
    public Product update(Product product,  Long id) {
        Product oldProduct = productRepository.findById(id).orElse(new Product());
        oldProduct.setName(product.getName());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setQuantity(product.getQuantity());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setSeller(product.getSeller());
        return productRepository.save(oldProduct);
    }
}
