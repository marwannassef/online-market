package com.miu.onlinemarket.service;

import java.util.List;
import java.util.Optional;

import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.domain.Review;
import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.domain.User;


public interface SellerService {

    Seller findSeller(String username);
    List<Product> searchByName(String name , long id);
    public User save(Seller seller);
    public Seller update(Seller seller);
    List<Seller> findUnApprovedSeller();
    Optional<Seller> findSellerById(Long id);
}
