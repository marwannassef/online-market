package com.miu.onlinemarket.service;

import com.miu.onlinemarket.domain.*;
import com.miu.onlinemarket.exceptionhandling.ResourceNotFoundException;

import java.util.List;

public interface BuyerService {

    public Buyer findByUsername(String username) throws ResourceNotFoundException;
    public Buyer save(Buyer buyer);
    public List<Buyer> listBuyer();
    public void deleteBuyer(Buyer buyer);
    public Buyer update(Buyer buyer) throws ResourceNotFoundException;
    public Buyer findBuyerById(Long userId) throws ResourceNotFoundException;
    public void updateAddress(Long userId, Address address) throws ResourceNotFoundException;
    public void updatePayment(Long userId, PaymentMethod paymentMethod ) throws ResourceNotFoundException;
    Buyer findBuyerBySellerId(Long id );
    List<Buyer> findBuyersBySellerId(Long id);

}
