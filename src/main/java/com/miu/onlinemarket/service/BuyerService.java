package com.miu.onlinemarket.service;

import com.miu.onlinemarket.domain.Buyer;
import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.domain.User;

public interface BuyerService {

    Buyer findBuyer(String username);

    public User save(Buyer buyer);

}
