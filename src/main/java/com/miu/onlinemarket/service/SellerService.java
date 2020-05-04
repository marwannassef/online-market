package com.miu.onlinemarket.service;

import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.domain.User;


public interface SellerService {

    Seller findSeller(String username);

    public User save(Seller seller);
}
