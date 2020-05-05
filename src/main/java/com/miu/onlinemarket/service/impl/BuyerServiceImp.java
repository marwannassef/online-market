package com.miu.onlinemarket.service.impl;

import com.miu.onlinemarket.domain.Address;
import com.miu.onlinemarket.domain.Buyer;
import com.miu.onlinemarket.domain.PaymentMethod;
import com.miu.onlinemarket.domain.User;
import com.miu.onlinemarket.repository.BuyerRepository;
import com.miu.onlinemarket.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyerServiceImp implements BuyerService {

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public Buyer save(Buyer buyer) {
        buyer.setPassword(encodePassword(buyer.getPassword()));
        return buyerRepository.save(buyer);
    }


    @Override
    public Buyer findByUsername(String username) {
        return buyerRepository.findBuyerByUsername(username);
    }


    @Override
    public List<Buyer> listBuyer() { return buyerRepository.findAll(); }

    @Override
    public void deleteBuyer(Buyer buyer) {
        buyerRepository.delete(buyer);
    }

    @Override
    public Buyer updateBuyer(Buyer buyer) {
        // note username constant can not change like most of websites
        String username = buyer.getUsername();
        Buyer oldBuyer = buyerRepository.findBuyerByUsername(username);
        oldBuyer.setPassword(buyer.getPassword());
        oldBuyer.setDateOfBirth(buyer.getDateOfBirth());
        oldBuyer.setEmail(buyer.getEmail());
        oldBuyer.setFirstName(buyer.getFirstName());
        oldBuyer.setLastName(buyer.getLastName());
        oldBuyer.setPhoneNumber(buyer.getPhoneNumber());
        oldBuyer.setShippingAddress(buyer.getShippingAddress());
        oldBuyer.setPaymentMethod(buyer.getPaymentMethod());

        oldBuyer.setOrders(buyer.getOrders());
        oldBuyer.setShippingAddress(buyer.getShippingAddress());
        oldBuyer.setPaymentMethod(buyer.getPaymentMethod());

        return save(oldBuyer);
    }



    @Override
    public Buyer findBuyerById(Long userId) {
        return buyerRepository.findById(userId).get();
    }

    @Override
    public void updateAddress(Long userId, Address address) {
        Buyer buyer = buyerRepository.findById(userId).get();
        buyer.setShippingAddress(address);
        buyerRepository.save(buyer);
    }

    @Override
    public void updatePayment(Long userId, PaymentMethod paymentMethod) {
        Buyer buyer = buyerRepository.findById(userId).get();
        buyer.setPaymentMethod(paymentMethod);
        buyerRepository.save(buyer);
    }
}
