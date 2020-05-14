package com.miu.onlinemarket.service;

import java.util.List;

import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.exceptionhandling.ResourceNotFoundException;

public interface ProductService {

	List<Product> findAll();

	List<Product> searchByName(String name);

	Product findById(Long id) throws ResourceNotFoundException;

	Product save(Product product);

	void delete(Product product);

	public Product update(Product product, Long id) throws ResourceNotFoundException;
}
