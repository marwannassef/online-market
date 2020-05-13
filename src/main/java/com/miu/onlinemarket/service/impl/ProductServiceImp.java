package com.miu.onlinemarket.service.impl;

import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.exceptionhandling.ResourceNotFoundException;
import com.miu.onlinemarket.repository.ProductRepository;
import com.miu.onlinemarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

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
    public Product findById(Long id) throws ResourceNotFoundException {
    	Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product with id " + id +" not found"));
    	if (product.getPhoto() != null && product.getPhoto().length != 0)
    		product.setPhotoBase64(Base64.getEncoder().encodeToString(product.getPhoto()));
        return product;
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
    public Product update(Product product,  Long id) throws ResourceNotFoundException {
        Product oldProduct = productRepository.findById(id).orElse(new Product());
        if(oldProduct == null) {
            throw new ResourceNotFoundException("Product with username " + oldProduct.getName() + " is not found");
        }
        oldProduct.setName(product.getName());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setQuantity(product.getQuantity());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setSeller(product.getSeller());
        return productRepository.save(oldProduct);
    }
}
