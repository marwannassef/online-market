package com.miu.onlinemarket.service;

import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.domain.User;

import java.util.List;


public interface SellerService {

    Seller findSeller(String username);
    List<Product> searchByName(String name , long id);
    public User save(Seller seller);
}
