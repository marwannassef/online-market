package com.miu.onlinemarket.service;

import java.util.List;

import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.domain.User;
import com.miu.onlinemarket.exceptionhandling.ResourceNotFoundException;

public interface SellerService {

	Seller findSeller(String username) throws ResourceNotFoundException;

	List<Product> searchByName(String name, long id);

	public User save(Seller seller);

	public Seller update(Seller seller) throws ResourceNotFoundException;

	List<Seller> findUnApprovedSeller();

	Seller findSellerById(Long id) throws ResourceNotFoundException;

	List<Seller> findSellersByBuyerId(Long id);

}
