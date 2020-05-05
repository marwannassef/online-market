package com.miu.onlinemarket.service;

import com.miu.onlinemarket.domain.*;

import java.util.List;

public interface BuyerService {

    public Buyer findByUsername(String username);
    public Buyer save(Buyer buyer);
    public List<Buyer> listBuyer();
    public void deleteBuyer(Buyer buyer);
    public Buyer updateBuyer(Buyer buyer);
    public Buyer findBuyerById(Long userId);
    public void updateAddress(Long userId, Address address);
    public void updatePayment(Long userId, PaymentMethod paymentMethod );

}
